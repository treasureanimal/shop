package com.study.gmall.interceptors;

import com.study.core.utils.CookieUtils;
import com.study.core.utils.JwtUtils;
import com.study.gmall.cart.pojo.UserInfo;
import com.study.gmall.config.JwtProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

/**
 * 定义拦截器
 */
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class LonginInterceptors extends HandlerInterceptorAdapter {

    @Autowired
    private JwtProperties jwtProperties;

    private static final ThreadLocal<UserInfo> THREAD_LOCAL= new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        UserInfo userInfo = new UserInfo();
        //获取cookie中的token信息（token）以及userKey
        String token = CookieUtils.getCookieValue(request, this.jwtProperties.getCookieName());

        //判断有没有token
        if (StringUtils.isNotEmpty(token)) {
            //解析token
            Map<String, Object> infoFromToken = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
            userInfo.setId(new Long(infoFromToken.get("id").toString()));
        }
        THREAD_LOCAL.set(userInfo);
        return true;
    }

    public static UserInfo getUserInfo(){
        return THREAD_LOCAL.get();
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       //因为使用tomcat线城池，会内存泄漏，必须手动清除threadLocal中的线程变量
        THREAD_LOCAL.remove();
    }
}
