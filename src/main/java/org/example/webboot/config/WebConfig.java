package org.example.webboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源路径，映射/uploads/请求到实际目录
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("classpath:/static/uploads/");  // 映射到静态资源的路径
    }
}
