package com.github.springcloud.stockcrawler;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spring.SpringGeccoEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
//@MapperScan("com.github.springcloud.stockcrawler.dbdao")
@Configuration
public class StockcrawlerApplication {

//	@Bean
//	public SpringGeccoEngine initGecco(){
//		return new SpringGeccoEngine() {
//			@Override
//			public void init() {
//				System.out.println("开始抓取");
//				HttpRequest request = new HttpGetRequest("http://quote.stockstar.com/stock/stock_index.htm");
//				//查看网站的编码，填充在这里
//				request.setCharset("gb2312");
//				GeccoEngine.create()
//						.pipelineFactory(springPipelineFactory)
//						.classpath("com.github.springcloud.stockcrawler")
//						.start(request)
//						.interval(3000)
//						.loop(false)
//						.start();
//			}
//		};
//	}

	public static void main(String[] args) {
		SpringApplication.run(StockcrawlerApplication.class, args);
	}
}
