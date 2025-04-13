package org.example.webboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.webboot.repository")  // 确保扫描到仓库包
public class WebbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebbootApplication.class, args
        );
    }

}
