package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动入口
 *
 * @author AnGame
 */
@EnableCaching
@SpringBootApplication() //exclude = SecurityAutoConfiguration.class 关闭Security验证功能
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
