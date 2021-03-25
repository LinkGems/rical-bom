package com.wtrue.sprica.common.config;

import lombok.Builder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/3/25 7:46 PM
 */
@Builder
public class SwaggerConfig {

    private String title;

    private Contact contact;

    private String description;

    private String version;

    private String url;

    private String basePackagePath;

    public Docket createRestApiOfAnyPackage(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }

    public Docket createRestApiOfBasePackage(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage(basePackagePath)).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title(title).contact(contact).description(description).version(version).termsOfServiceUrl(url).build();
    }

}
