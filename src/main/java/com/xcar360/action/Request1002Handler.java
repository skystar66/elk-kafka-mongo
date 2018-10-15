package com.xcar360.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xcar360.es.Entity;
import com.xcar360.mongo.MessageTemplate;
import com.xcar360.service.CityESService;
import com.xcar360.util.KafkaConstants;
import com.xcar360.util.ReturnCode;
import com.xcar360.web.auth.AbstractRequest;
import com.xcar360.web.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author xulaing
 * @date 2018年10月11日 14:44:55
 */
@Component("request1002Handler")
public class Request1002Handler extends AbstractRequestHandler{

    @Autowired
    CityESService cityESService;

    @Override
    protected boolean checkParams(Object params) {
        return true;
    }

    @Override
    protected ResponseResult bizHandle(AbstractRequest request) throws RuntimeException, Exception {
        return handler(request);
    }




    /**
     * 版本10  生产消息 简单----
     *
     * @param request
     * @return
     */
    private ResponseResult handler(AbstractRequest request) throws Exception {
//        List<Entity> entity = JSON.parseObject(JSON.toJSONString(request.getParams()), List.class);
        List<Entity> entity = JSON.parseArray(JSON.toJSONString(request.getParams())).toJavaList(Entity.class);
        cityESService.saveEntity(entity);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
        responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
        responseResult.setData(entity);
        return responseResult;
    }









}
