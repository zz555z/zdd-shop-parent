package com.zdd;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author Xin
 * @date 2020/7/1 1:58 下午
 * @Content:
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2Doc
//@EnableApolloConfig
@ComponentScan({"com.zdd.product.service","com.zdd.core"})
@MapperScan(basePackages = "com.zdd.member.mapper")
@EnableElasticsearchRepositories(basePackages = {"com.zdd.product.es"})
public class AppProduct {
    public static void main(String[] args) {
        SpringApplication.run(AppProduct.class,args);
    }
}
