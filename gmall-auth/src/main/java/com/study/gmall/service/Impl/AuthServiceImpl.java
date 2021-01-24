package com.study.gmall.service.Impl;

import com.study.core.bean.Resp;
import com.study.core.utils.JwtUtils;
import com.study.gmall.config.JwtProperties;
import com.study.gmall.feign.GmallAuthClientApi;
import com.study.gmall.service.AuthService;
import com.study.gmall.ums.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthServiceImpl implements AuthService {

    @Autowired
    private GmallAuthClientApi gmallAuthClientApi;
    @Autowired
    private JwtProperties jwtProperties;


    @Override
    public String accredit(String userName, String passWord) {
        //远程调用ums，校验用户名和密码
        Resp<MemberEntity> memberEntityResp = this.gmallAuthClientApi.queryUser(userName, passWord);
        MemberEntity data = memberEntityResp.getData();
        //判断用户是否为null
        if (data == null) {
            return null;
        }
        try {
            //如果不为null制作jwt
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",data.getId());
            map.put("userName",data.getUsername());

            return JwtUtils.generateToken(map,this.jwtProperties.getPrivateKey(),this.jwtProperties.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
