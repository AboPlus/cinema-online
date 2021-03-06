package com.abo.util;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 测试配置类
 * @author : Abo
 * @date : 2022/1/21 18:21
 */
@Configuration
@EnableSwagger2 // 激活Swagger
public class SwaggerConfig {
    /** 设置接口信息 */
    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("API文档")
                .description("描述微服务接口定义")
                .version("1.0")
                .contact(new Contact("java", "", "abopluscn@gmail.com"))
                .build();
    }

    /** 构建接口文档 */
    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }
}
