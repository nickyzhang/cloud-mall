package com.cloud.common.core.config;


import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.cloud.api.*"))
            .paths(PathSelectors.any())
            .build();
        return docket;
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("cloud-mall")
                .description("Cloud Mall RESTful API")
                .termsOfServiceUrl("https://www.cloud.com")
                .contact(new Contact("cloud mall 技术博客","https://blog.csdn.net/zhanglh046","zhanglh046@foxmail.com"))
                .version("1.0")
                .build();
    }
}
