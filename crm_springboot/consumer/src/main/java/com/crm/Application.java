package com.crm;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@EnableDubboConfiguration//dubbo配置
@ServletComponentScan(basePackages = "com.crm.web")//过滤器
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
