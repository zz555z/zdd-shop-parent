package com.zdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Xin
 * @date 2021/3/11 4:57 下午
 * @Content:
 */


@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class AppPayWeb {
    public static void main(String[] args) {
        SpringApplication.run(AppPayWeb.class, args);

    }
}
