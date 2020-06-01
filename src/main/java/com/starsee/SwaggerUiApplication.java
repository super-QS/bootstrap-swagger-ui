package com.starsee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;

@SpringBootApplication
//@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class SwaggerUiApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerUiApplication.class, args);
    }

    /**
     * 解决访问 doc.html 报404 问题
     * 先添加 @ConditionalOnClass(SpringfoxWebMvcConfiguration.class) 注解
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
