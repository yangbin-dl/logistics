package com.mallfe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//@EnableDiscoveryClient
@MapperScan("com.mallfe.item.mapper")
public class MfItemService {
    public static void main(String[] args) {
        SpringApplication.run(MfItemService.class);
    }
}
