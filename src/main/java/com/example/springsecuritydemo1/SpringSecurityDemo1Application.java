package com.example.springsecuritydemo1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@MapperScan("com.example.springsecuritydemo1.mapper")
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SpringSecurityDemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemo1Application.class, args);
    }

}
