package com.xcar360.action;

import com.alibaba.fastjson.JSON;
import com.xcar360.kafka.model.MessageTemplate;
import com.xcar360.util.ReturnCode;
import com.xcar360.web.auth.AbstractRequest;
import com.xcar360.web.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
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

    @Override
    protected boolean checkParams(Map<String, Serializable> params) {
        //校验参数
        return true;
    }

    @Override
    protected ResponseResult bizHandle(AbstractRequest request) throws Exception {
        //动态分配版本号 daima  版本迭代

        return handler(request);
    }


    /**
     * 版本10  生产消息 简单----
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
        messageTemplate.setTopic("testlog");
        messageTemplate.setMmessgeType("1");
        messageTemplate.setSendTime(new Date());

        String dataStr = JSON.toJSONString(messageTemplate);
        kafkaTemplate.send("testlog",dataStr);


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
        Map<String, Object> commonParams = new HashMap<>();
//        commonParams.put("frontId", StringUtil.getStringOfObject(request.getParam("frontId")));
        return commonParams;
    }
}
