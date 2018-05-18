package com.github.springcloud.stockcrawler.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ganzhen on 2018/5/18.
 */
@Configuration
public class RabbitmqConfig {
    @Value("${spring.rabbitmq.queuename}")
    private String queuename;

    @Value("${spring.rabbitmq.exchange}")
    private String queueExchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.rabbitmq")
    public ConnectionFactory connectionFactory(){
        return new CachingConnectionFactory();//publisherconfirms 消息确认回调
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory connectionFactory, MessageConverter converter){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(true);
        template.setExchange(queueExchange);
        template.setRoutingKey(routingkey);
        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    public Queue queue(){
        return new Queue(queuename,true);
    }
}
