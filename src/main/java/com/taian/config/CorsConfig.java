package com.taian.config;

import com.taian.util.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/**")  //拦截所有请求
                .excludePathPatterns("/sys/register")//开放登录路径
                .excludePathPatterns("/upload/**")//开放获取图片路径
                .excludePathPatterns("/admin/**");//开放管理员后台请求
    }
}
