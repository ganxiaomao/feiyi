package com.github.springcloud.stockcrawler.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

/**
 * Created by ganzhen on 2018/5/18.
 */
@Service
public class TestProducerService extends BasicProducerService{
    private Logger logger = LoggerFactory.getLogger(TestProducerService.class);

    public void send(String remark){
        this.sendMessage("consumerService", "getMessage", "1234",null);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("回调id："+correlationData);
        if(ack)
            logger.info("消息发送成功");
        else
            logger.info("消息发送失败，原因为："+cause);
    }
}
