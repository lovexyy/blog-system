package com.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: blog-admin
 * @description: 后端跨越问题解决，或者中间件 nginx
 * @author: Zx
 * @create: 2022-08-03 14:38
 **/
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        /**
         * 如果你的springboot版本较低，在2.2以下，具体那个版本我没有试过，跨域配置需要将 .allowedOriginPatterns 替换成 .allowedOrigins，
         * 因为在新版本SpringBoot中，跨域配置将 .allowedOrigins 替换成 .allowedOriginPatterns了
         */
        registry.addMapping("/**")
                // 设置允许跨域请求的域名------------修改此行
                .allowedOriginPatterns("*")
                //.allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
