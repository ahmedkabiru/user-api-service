package com.oneworldaccuracy.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 05/04/2021
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public Docket apiDocket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.oneworldaccuracy.userservice"))
                .paths(regex("/api.*"))
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "USER SERVICE",
                "User Service api",
                "1.0.0",
                "TERMS OF SERVICE URL",
                new Contact("Ahmed Kabir Opeyemi", "http://www.hamsoft.com.ng", "opeyemi.kabiru@yahoo.com"),
                "MIT License",
                "LICENSE URL",
                Collections.emptyList()
        );

    }


}
