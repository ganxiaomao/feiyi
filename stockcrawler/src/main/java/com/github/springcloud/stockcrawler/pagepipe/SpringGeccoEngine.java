package com.github.springcloud.stockcrawler.pagepipe;

import com.geccocrawler.gecco.pipeline.PipelineFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * Created by ganzhen on 12/02/2018.
 */
public abstract class SpringGeccoEngine implements ApplicationListener<ContextRefreshedEvent>{

    @Resource
    protected PipelineFactory springPipelineFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null){
            init();
        }
    }

    public abstract void init();
}
