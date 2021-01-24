package com.study.gmall.config;

import com.study.gmall.interceptors.LonginInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GmallWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LonginInterceptors longinInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(longinInterceptors).addPathPatterns("/**");  //添加拦截器,拦截购物车中的所有请求
    }
}
