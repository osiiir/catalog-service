package com.osir.catalogservice;

import com.osir.commonservice.config.FeignHeaderConfig;
import com.osir.commonservice.utils.LoginUserContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@Import(FeignHeaderConfig.class) // 引入feign配置类,传递头信息
@EnableDiscoveryClient
@SpringBootApplication
public class CatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }

}
