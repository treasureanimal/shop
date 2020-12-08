package com.study.core.config;

import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


/**
 * 数据源配置
 *
 * @author HelloWoodes
 */
@Configuration
public class DataSourceConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariDataSource hikariDataSource(@Value("${spring.datasource.url}") String url) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(url);
        return hikariDataSource;
    }

    /**
     * 需要将 DataSourceProxy 设置为主数据源，否则事务无法回滚
     *
     * @return The default datasource
     */
    @Primary
    @Bean("dataSource")
    public DataSource dataSource(@Value("${spring.datasource.url}") String url,@Value("${spring.datasource.username}") String userName,
                                 @Value("${spring.datasource.password}") String passWord,@Value("${spring.datasource.driver-class-name}") String driverClassName) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(userName);
        hikariDataSource.setPassword(passWord);
        hikariDataSource.setDriverClassName(driverClassName);
        return new DataSourceProxy(hikariDataSource);
    }
}