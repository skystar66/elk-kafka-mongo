package com.xcar360.web.auth;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xulaing
 * @date 2018年10月11日 14:44:55
 */
public class Request1001 extends AbstractRequest {
    public Request1001( String apiId, long timestamp) {
        super(apiId, timestamp);
    }

    @Override
    protected void getBizParams(JSONObject params) {
        String paramsStr = params.getString("params");
        if(paramsStr==null){
            return ;
        }
        Map<String,Serializable> map = JSONObject.parseObject(paramsStr, HashMap.class);
        setParams(map);
    }
}
