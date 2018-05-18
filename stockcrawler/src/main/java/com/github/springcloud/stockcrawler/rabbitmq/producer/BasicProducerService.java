package com.github.springcloud.stockcrawler.rabbitmq.producer;

import com.eaio.uuid.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by ganzhen on 2018/5/18.
 */
public abstract class BasicProducerService implements RabbitTemplate.ConfirmCallback{
    private Logger logger = LoggerFactory.getLogger(BasicProducerService.class);

    @Resource
    public RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    @Value("${spring.rabbitmq.appid}")
    private String appid;

    public void sendMessage(final String serviceName, final String serviceMethodName, final String correlationId, Object request){
        logger.info("sendMessage [this.{}, serviceMethodName:{} serviceName:{} correlationId: {}]", this.getClass(), serviceMethodName, serviceName, correlationId);

        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setCorrelationKey(correlationId);
        rabbitTemplate.convertAndSend(routingkey, request, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setAppId(appid);
                message.getMessageProperties().setTimestamp(new Date());
                message.getMessageProperties().setMessageId(new UUID().toString());
                message.getMessageProperties().setCorrelationId(correlationId);
                message.getMessageProperties().setHeader("ServiceMethodName", serviceMethodName);
                message.getMessageProperties().setHeader("ServiceName", serviceName);
                return message;
            }
        }, new CorrelationData(correlationId));
    }

    /**
     * 抽象回调方法
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public abstract void confirm(CorrelationData correlationData, boolean ack, String cause);
}
