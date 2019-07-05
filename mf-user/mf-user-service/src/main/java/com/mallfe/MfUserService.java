package com.mallfe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/05
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.mallfe.user.mapper")
public class MfUserService {
    public static void main(String[] args) {
        SpringApplication.run(MfUserService.class);
    }
}
