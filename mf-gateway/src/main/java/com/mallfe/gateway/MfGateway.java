package com.mallfe.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringCloudApplication
public class MfGateway {
    public static void main(String[] args) {
        SpringApplication.run(MfGateway.class);
    }
}
