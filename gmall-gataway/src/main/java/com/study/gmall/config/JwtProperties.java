package com.study.gmall.config;

import com.study.core.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 读取配置文件中的gmall下jwt参数
 * 1.@ConfigurationProperties
 * 2.在使用该类的类上边加@EnableConfigurationProperties
 * 3.添加和application.yml文件中的配置文件相对应的属性字段
 */
@Data
@ConfigurationProperties(prefix = "gmall.jwt")
@Slf4j
public class JwtProperties {


    private String pubKeyPath;// 公钥

    private PublicKey publicKey; // 公钥

    private String cookieName; // cookie名称

    /**
     * @PostContruct：在构造方法执行之后执行该方法
     */
    @PostConstruct
    public void init() {
        try {
            File pubKey = new File(pubKeyPath);
            // 获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            log.error("初始化公钥和私钥失败！", e);
            throw new RuntimeException();
        }
    }
}
