package com.starsee.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpSession;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
//@EnableKnife4j // 在2.0.1之后，使用此注解,舍弃 @EnableSwaggerBootstrapUI 注解
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))

                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(HttpSession.class);//添加忽略类型

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .termsOfServiceUrl("localhost:8080/")
                .contact("123@qq.com")
                .version("1.0")
                .description("接口文档")
                .build();
    }
}
