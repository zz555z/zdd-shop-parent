package com.zdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Xin
 * @date 2020/8/20 5:28 下午
 * @Content:
 */
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class  AppPortalWeb {
    public static void main(String[] args) {
        SpringApplication.run(AppPortalWeb.class, args);

    }
}
