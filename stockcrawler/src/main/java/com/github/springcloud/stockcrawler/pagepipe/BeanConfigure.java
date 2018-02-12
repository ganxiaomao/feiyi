package com.github.springcloud.stockcrawler.pagepipe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ganzhen on 12/02/2018.
 */
@Configuration
public class BeanConfigure {

    @Bean
    public SpringPipelineFactory springPipelineFactory(){
        return new SpringPipelineFactory();
    }

    @Bean(name = "consolePipeline")
    public ConsolePipeline consolePipeline(){
        return new ConsolePipeline();
    }

    @Bean(name = "stockStarComPipeline")
    public StockStarComPipeline stockStarComPipeline(){
        return new StockStarComPipeline();
    }
}
