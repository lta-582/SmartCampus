package com.taian.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + "E:/upload/datasrc" + "/");
        registry.addResourceHandler("/uploadtmp/**").addResourceLocations("file:" + "E:/upload/datastmp" + "/");
    }
}
