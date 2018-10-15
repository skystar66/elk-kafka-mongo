package com.xcar360.web.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xulaing
 * @date 2018年10月11日 14:44:55
 */
public class Request1002 extends AbstractRequest {
    public Request1002(String apiId, long timestamp) {
        super(apiId, timestamp);
    }

    @Override
    protected void getBizParams(JSONObject params) {
        String paramsStr = params.getString("params");
        if(paramsStr==null){
            return ;
        }

        getJSONType(paramsStr);
    }



}
