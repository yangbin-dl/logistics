package com.mallfe.gateway.cart.config;

import com.mallfe.common.utils.RsaUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author: HuYi.Zhang
 * @create: 2018-05-26 21:53
 **/
@Configuration
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

//    private String pubKeyPath;// 公钥

    private PublicKey publicKey; // 公钥

    private String pubKeyPath;

    private String cookieName;

    private static final Logger logger = LoggerFactory.getLogger(JwtProperties.class);

    @PostConstruct
    public void init(){
        try {
//            RsaUtils.generateKey(pubKeyPath,"/Users/xudong/Desktop/logistics/mf-gateway/src/main/resources/pri.key","luck");
            // 获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
//            String stringKey ="lwlsecre";
//            byte[] encodedKey = Base64.getDecoder().decode(stringKey);
//            PKCS8EncodedKeySpec x509EncodedKeySpec = new PKCS8EncodedKeySpec(encodedKey);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA2");
//            this.publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
//            this.publicKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("初始化公钥失败！", e);
            throw new RuntimeException(e);
        }
    }



}
