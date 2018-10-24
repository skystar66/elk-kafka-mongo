package com.xcar360.kafka.config;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;

/**
 * @author xulaing
 * @date 2018年10月11日 14:44:55
 * @desc     消息发送回调(ListenableFutureCallback,ProducerListener)
 */
@Component
public class KafkaSendResultHandler implements ProducerListener {





    private static final Logger log = LoggerFactory.getLogger(KafkaSendResultHandler.class);
//
//    /*
//    *
//    * 发送消息成功的时候回调用OnSuccess方法
//    * */
//
//    @Override
//    public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
//        log.info("Message send success : " + producerRecord.toString());
//    }
//
//    /*
//    *
//    * 发送失败则会调用onError方法
//    * */
//
//    @Override
//    public void onError(ProducerRecord producerRecord, Exception exception) {
//        log.info("Message send error : " + producerRecord.toString()+"异常信息："+exception.getMessage());
//    }

    /*
    *
    * 发送消息成功的时候回调用OnSuccess方法
    * */

    @Override
    public void onSuccess(String topic, Integer partition, Object key, Object value, RecordMetadata recordMetadata) {
        log.info("==========kafka发送数据成功（日志开始）==========");
        log.info("----------topic:"+topic);
        log.info("----------partition:"+partition);
        log.info("----------key:"+key);
        log.info("----------value:"+value);
        log.info("----------RecordMetadata:"+recordMetadata);
        log.info("~~~~~~~~~~kafka发送数据成功（日志结束）~~~~~~~~~~");
    }

     /*
    *
    * 发送失败则会调用onError方法
    * */

    @Override
    public void onError(String topic, Integer partition, Object key, Object value, Exception exception) {
        log.info("==========kafka发送数据错误（日志开始）==========");
        log.info("----------topic:"+topic);
        log.info("----------partition:"+partition);
        log.info("----------key:"+key);
        log.info("----------value:"+value);
        log.info("----------Exception:"+exception);
        log.info("~~~~~~~~~~kafka发送数据错误（日志结束）~~~~~~~~~~");
        exception.printStackTrace();
    }

    /**
     * 方法返回值代表是否启动kafkaProducer监听器
     */

    @Override
    public boolean isInterestedInSuccess() {
        log.info("///kafkaProducer监听器启动///");
        return true;
    }


//    @Override
//    public void onFailure(Throwable throwable) {
//        log.info("Message send success : "+throwable.toString());
//    }
//
//    @Override
//    public void onSuccess(Object o) {
//        log.info("Message send success : "+o);
//    }
}
