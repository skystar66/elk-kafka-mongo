package com.xcar360.kafka.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.GenericErrorHandler;
import org.springframework.stereotype.Component;

/**
 * @author xulaing
 * @date 2018年10月24日 11:44:55
 * @desc     消息消费回调(ListenableFutureCallback,ProducerListener)
 */
@Component
public class KafkaGenericErrorHandler implements GenericErrorHandler {


    private static Logger logger = LoggerFactory.getLogger(KafkaGenericErrorHandler.class);


    @Override
    public void handle(Exception e, Object o) {
        logger.info("消息消费发生异常，异常信息：{}，消费信息：{}",e.getMessage(),o);
    }
}
