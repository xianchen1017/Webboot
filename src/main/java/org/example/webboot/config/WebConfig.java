package org.example.webboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("classpath:/static/uploads/"); // 映射到 uploads 文件夹

        registry.addResourceHandler("/files/**")
                .addResourceLocations("classpath:/static/files/"); // 映射到 files 文件夹
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/contact/**")
                .allowedOrigins("http://localhost:3000")  // 前端应用所在的域名
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}

