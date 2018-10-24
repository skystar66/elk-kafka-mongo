package com.xcar360.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xcar360.kafka.config.KafkaSendResultHandler;
import com.xcar360.mongo.MessageTemplate;
import com.xcar360.util.KafkaConstants;
import com.xcar360.util.ReturnCode;
import com.xcar360.web.auth.AbstractRequest;
import com.xcar360.web.response.ResponseResult;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author xulaing
 * @date 2018年10月11日 14:44:55
 */
@Component("request1001Handler")
public class Request1001Handler extends AbstractRequestHandler {
    final protected static Logger logger = LoggerFactory.getLogger(Request1001Handler.class);

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Autowired
    KafkaSendResultHandler kafkaSendResultHandler;

    @Override
    protected boolean checkParams(Object params) {
        //校验参数
        return true;
    }

    @Override
    protected ResponseResult bizHandle(AbstractRequest request) throws Exception {
        //动态分配版本号 daima  版本迭代

        return handler(request);
    }


    /**
     * 版本10  生产消息 简单----1
     *
     * @param request
     * @return
     */
    private ResponseResult handler(AbstractRequest request) throws Exception {
        //记录请求日志
        MessageTemplate messageTemplate = new MessageTemplate();

        messageTemplate.setMessageId(UUID.randomUUID().toString().replace("-",""));
        messageTemplate.setMessageInfo("测试服务");
//        messageTemplate.setMessageQueueName("testlog");
        messageTemplate.setTopic(KafkaConstants.KAFKA_TEST1_TOPIC_MESSAGE);
        messageTemplate.setMmessgeType("1");
        messageTemplate.setSendTime(new Date());

        String dataStr = JSON.toJSONString(messageTemplate);

        //获取key_cid_str
        String key_cid_str = dataStr.substring(dataStr.indexOf(":")+1, dataStr.indexOf(","));

        //使用监听 回调
        kafkaTemplate.setProducerListener(kafkaSendResultHandler);

        //kafka异步发送消息
        kafkaTemplate.send(KafkaConstants.KAFKA_TEST1_TOPIC_MESSAGE, key_cid_str, dataStr);


//        kafkaTemplate.send("kafkatest",dataStr);
        //kafka同步发送消息
//        kafkaTemplate.send(KafkaConstants.KAFKA_TEST1_TOPIC,dataStr).get();

        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
        responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
        responseResult.setData(messageTemplate);
        return responseResult;
    }

    /**
     * 解析参数
     *
     * @param request
     * @return
     */
    private Map<String, Object> getCommonParams(AbstractRequest request) {
        Map<String, Object> commonParams = new HashMap<String, Object>();
//        commonParams.put("frontId", StringUtil.getStringOfObject(request.getParam("frontId")));
        return commonParams;
    }
}
