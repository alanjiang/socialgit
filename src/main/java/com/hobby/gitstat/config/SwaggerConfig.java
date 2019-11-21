package com.hobby.gitstat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Alan P Jiang
 * @ClassName com.amazon.aws.exam.config.SwaggerConfig
 * @Description
 * @date 2019-07-19 22:12:31
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.amazon.aws.exam"))
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("AWS short-url service API")
				.description("更多精彩请参观：http://18.139.178.244:8080/")
				
				.version("1.0")
				.build();
	}
}
