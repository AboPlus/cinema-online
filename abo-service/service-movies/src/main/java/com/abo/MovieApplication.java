package com.abo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author : Abo
 * @date : 2022/1/24 15:58
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@MapperScan("com.abo.mapper")
public class MovieApplication {
    public static void main(String[] args) {
        SpringApplication.run(MovieApplication.class,args);
    }
}
