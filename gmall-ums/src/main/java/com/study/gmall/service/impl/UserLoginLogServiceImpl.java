package com.study.gmall.service.impl;

import com.study.gmall.dao.UserLoginLogDao;
import com.study.gmall.service.UserLoginLogService;
import com.study.gmall.ums.entity.UserLoginLogEntity;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.core.bean.PageVo;
import com.study.core.bean.Query;
import com.study.core.bean.QueryCondition;



@Service("userLoginLogService")
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogDao, UserLoginLogEntity> implements UserLoginLogService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<UserLoginLogEntity> page = this.page(
                new Query<UserLoginLogEntity>().getPage(params),
                new QueryWrapper<UserLoginLogEntity>()
        );

        return new PageVo(page);
    }

}