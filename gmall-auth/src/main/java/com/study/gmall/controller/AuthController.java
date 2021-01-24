package com.study.gmall.controller;

import com.study.core.bean.Resp;
import com.study.core.exception.MemberException;
import com.study.core.utils.CookieUtils;
import com.study.gmall.config.JwtProperties;
import com.study.gmall.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtProperties jwtProperties;
    @PostMapping("/accredit")
    public Resp<Object> accredit(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord,
                                 HttpServletRequest request, HttpServletResponse response){
        String token = this.authService.accredit(userName,passWord);
        if (StringUtils.isNotBlank(token)) {
            //放入cookie中cookie中的过期时间默认过期时间为秒
            CookieUtils.setCookie(request,response,this.jwtProperties.getCookieName(),token,this.jwtProperties.getExpire()*60);
            return Resp.ok(null);
        }
        throw new MemberException("用户名和密码错误");
    }
}
