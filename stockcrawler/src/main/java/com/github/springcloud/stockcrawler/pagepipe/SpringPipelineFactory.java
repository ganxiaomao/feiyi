package com.github.springcloud.stockcrawler.pagepipe;

import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.pipeline.PipelineFactory;
import com.geccocrawler.gecco.spider.SpiderBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by ganzhen on 12/02/2018.
 */
public class SpringPipelineFactory implements PipelineFactory, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Pipeline<? extends SpiderBean> getPipeline(String s) {
        try {
            Object bean = applicationContext.getBean(s);
            if(bean instanceof Pipeline) {
                System.out.println("pipeline name:"+s);
                return (Pipeline<? extends SpiderBean>)bean;
            }
        } catch(NoSuchBeanDefinitionException ex) {
            System.out.println("no such pipeline : " + s);
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
