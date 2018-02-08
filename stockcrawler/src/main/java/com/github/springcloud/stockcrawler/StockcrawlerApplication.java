package com.github.springcloud.stockcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StockcrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockcrawlerApplication.class, args);
	}
}
