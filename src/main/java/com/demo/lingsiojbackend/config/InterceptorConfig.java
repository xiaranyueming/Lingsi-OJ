package com.demo.lingsiojbackend.config;

import com.demo.lingsiojbackend.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register", "/swagger-resources/**", "/webjars/**", "/v3/**", "/swagger-ui.html/**",
                        "/api", "/api-docs", "/api-docs/**", "/doc.html/**");
    }
}
