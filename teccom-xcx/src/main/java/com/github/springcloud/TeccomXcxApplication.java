package com.github.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.github.springcloud.teccom.dbdao")
@Configuration
public class TeccomXcxApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeccomXcxApplication.class, args);
	}
}
