package com.zdd;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * 微服务网关，  Swagger 配置
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableSwagger2Doc
public class AppGateWay {

	public static void main(String[] args) {
		SpringApplication.run(AppGateWay.class, args);
	}

	// 添加文档来源
	@Component
	@Primary
	class DocumentationConfig implements SwaggerResourcesProvider {
		@Override
		public List<SwaggerResource> get() {
			List resources = new ArrayList<>();
			//  使用服务的别名 获取远程服务api
			resources.add(swaggerResource("app-zdd-member", "/app-zdd-member/v2/api-docs", "2.0"));
			resources.add(swaggerResource("app-zdd-weixin", "/app-zdd-weixin/v2/api-docs", "2.0"));
			return resources;
		}

		private SwaggerResource swaggerResource(String name, String location, String version) {
			SwaggerResource swaggerResource = new SwaggerResource();
			swaggerResource.setName(name);
			swaggerResource.setLocation(location);
			swaggerResource.setSwaggerVersion(version);
			return swaggerResource;
		}

	}



}