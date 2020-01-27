package com.secl.eservice.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(Arrays.asList("application/json"));
	private static final Contact DEFAULT_CONTACT = new Contact("Md. Kamruzzaman Tanim", "http://www.google.com", "tanim1109135@gmail.com");
    
	@Bean
	public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        	.groupName("Security-api")
            .apiInfo(apiInfo())
	        .produces(DEFAULT_PRODUCES_AND_CONSUMES)
	        .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.secl.eservice"))
            .paths(PathSelectors.any())
            .build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Security API").description("Security API reference for developers")
			.termsOfServiceUrl("http://www.google.com").license("SECL License")
			.licenseUrl("tanim1109135@gmail.com").version("1.0")
			.contact(DEFAULT_CONTACT).build();
	}

}
