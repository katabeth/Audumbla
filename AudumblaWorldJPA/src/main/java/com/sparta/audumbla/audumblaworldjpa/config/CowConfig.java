package com.sparta.audumbla.audumblaworldjpa.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CowConfig implements WebMvcConfigurer {
    private final CowConfig cowInterceptor;

    @Autowired
    public CowConfig(CowConfig cowInterceptor) {
        this.cowInterceptor = cowInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cowInterceptor);
    }
}
