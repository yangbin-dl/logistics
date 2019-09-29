package com.mallfe.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1) 允许的域,不要写*，否则cookie就无法使用了
        config.addAllowedOrigin("http://www.mallfe.com:9001");
        config.addAllowedOrigin("http://localhost:9002");
        config.addAllowedOrigin("http://localhost:10010");
        config.addAllowedOrigin("http://127.0.0.1:9002");
        config.addAllowedOrigin("http://47.105.221.242:81");
        config.addAllowedOrigin("http://172.31.53.68");
        config.addAllowedOrigin("http://47.105.221.242");
        config.addAllowedOrigin("http://172.17.196.112");
        config.addAllowedOrigin("http://47.94.95.93");
        config.addAllowedOrigin("http://47.105.221.242:9002");

        //2) 是否发送Cookie信息
        config.setAllowCredentials(true);
        //3) 允许的请求方式
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");


        // 4）允许的头信息
        config.addAllowedHeader("*");
//
//        config.addAllowedHeader("Access-Control-Allow-Origin", "*");
//        config.addAllowedHeader("Access-Control-Allow-Methods", "*");
//        config.addAllowedHeader("Access-Control-Allow-Headers", "*");
//        config.addAllowedHeader("Access-Control-Request-Headers","*");
//        config.addAllowedHeader("Access-Control-Allow-Headers", "content-type,x-auth-token");
//        config.addAllowedHeader("Access-Control-Expose-Headers", "*");
        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);
    }
}
