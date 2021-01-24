package com.study.gmall.filter;

import com.study.core.utils.JwtUtils;
import com.study.gmall.config.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@EnableConfigurationProperties(JwtProperties.class)
public class AuthGatewayFilter implements GatewayFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //1.获取jwt类型的token信息
        //1.1先拿到cookie,如果用户禁用cookie
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        if (CollectionUtils.isEmpty(cookies)) {
            //拦截
            response.setStatusCode(HttpStatus.UNAUTHORIZED); //返回的错误码
            return response.setComplete();  //请求从这直接返回
        }
        HttpCookie cookie = cookies.getFirst(this.jwtProperties.getCookieName());//因为域名中有可能有多个cookie，子域名也有可能拥有相同名字的cookie所以就取第一个
        //判断jwt类型的token是否为null
        if (cookie == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        try {
            //解析jwt.如果抛异常说明解析失败进行拦截，如果解析成功可以放行
            JwtUtils.getInfoFromToken(cookie.getValue(),this.jwtProperties.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //chain过滤器链放行
        return chain.filter(exchange);
    }
}
