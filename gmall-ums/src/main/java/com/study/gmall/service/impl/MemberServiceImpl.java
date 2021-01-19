package com.study.gmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;
import com.study.core.exception.MemberException;
import com.study.gmall.dao.MemberDao;
import com.study.gmall.service.MemberService;
import com.study.gmall.ums.entity.MemberEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public Boolean checkDate(String data, Integer type) {
        QueryWrapper<MemberEntity> memberEntityQueryWrapper = new QueryWrapper<>();
        switch (type){
            case 1 : memberEntityQueryWrapper.eq("username",data); break;
            case 2 : memberEntityQueryWrapper.eq("mobile",data);break;
            case 3 : memberEntityQueryWrapper.eq("emall",data);break;
            default:
                return false;
        }
        return this.count(memberEntityQueryWrapper) == 0;
    }

    @Override
    public void register(MemberEntity memberEntity, String code) {

        //1.生成手机验证码

        //2.生成盐
        String salt = UUID.randomUUID().toString().substring(0, 6);
        memberEntity.setSalt(salt);
        //3.加盐加密
        memberEntity.setPassword(DigestUtils.md5Hex(memberEntity.getPassword() + salt));
        //4.生成用户
        // 新增用户
        memberEntity.setGrowth(0);
        memberEntity.setIntegration(0);
        memberEntity.setLevelId(0l);
        memberEntity.setCreateTime(new Date());
        memberEntity.setStatus(1);
        this.save(memberEntity);
        // 删除redis中的验证码: TODO
    }

    @Override
    public MemberEntity queryUser(String username, String password) {
        // 根据用户名查询用户
        MemberEntity memberEntity = this.getOne(new QueryWrapper<MemberEntity>().eq("username", username));

        // 判断用户是否存在
        if (memberEntity == null) {
            throw new MemberException("用户名不存在！");
        }

        // 先对用户输入的密码加盐加密
        password = DigestUtils.md5Hex(password + memberEntity.getSalt());

        // 比较数据库中的密码和用户输入的密码是否一致
        if (!StringUtils.equals(password, memberEntity.getPassword())) {
            throw new MemberException("密码错误！");
        }

        return memberEntity;
    }

}