package com.study.gmall;

import com.study.core.config.DataSourceConfig;
import com.study.core.config.MybatisPlusConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@EnableSwagger2
@ComponentScan(basePackages={"com.study"},excludeFilters =@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {DataSourceConfig.class, MybatisPlusConfig.class}))
@SpringBootApplication
@EnableFeignClients
@Configuration
public class SearchApplication {
    public static void main(String[] args) { SpringApplication.run(SearchApplication.class, args); }
}
