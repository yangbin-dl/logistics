package com.mallfe.logistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019-07-06
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.mallfe.logistics.mapper")
public class MfLogisticsService {
    public static void main(String[] args) {
        SpringApplication.run(MfLogisticsService.class);
    }
}
