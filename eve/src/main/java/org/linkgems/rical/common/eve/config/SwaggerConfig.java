package org.linkgems.rical.common.eve.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description: Swagger文档，取各自application.yml配置。调用端口为：
 *                  http://{ip}:{port}/doc.html
 * @author: meidanlong
 * @date: 2021/3/25 7:43 PM
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.title}")
    private String title;
    @Value("${swagger.description}")
    private String description;
    @Value("${swagger.author}")
    private String author;
    @Value("${swagger.email}")
    private String email;
    @Value("${swagger.version}")
    private String version;
    @Value("${swagger.web}")
    private String web;
    @Value("${swagger.self-url}")
    private String selfUrl;

    @Bean
    public Docket swaggerDocket(){
        Contact contact = new Contact(author, selfUrl, email);
        Swagger swagger = Swagger.builder()
                .title(title)
                .contact(contact)
                .description(description)
                .version(version)
                .url(web)
                .build();
        Docket docket = swagger.createRestApiOfAnyPackage();
        System.out.println(docket.toString());
        return swagger.createRestApiOfAnyPackage();
    }

}
