package com.cloud.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages="com.cloud")
public class DmUserProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DmUserProviderApplication.class, args);
    }

}
