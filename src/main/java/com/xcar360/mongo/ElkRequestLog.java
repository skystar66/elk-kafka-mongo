package com.xcar360.mongo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
/**
 * @author xulaing
 * @date 2018年10月11日 14:44:55
 */
@Document(collection = "elk_system_log")
public class ElkRequestLog {
    @Id
    @JsonIgnore
    protected ObjectId id;
    @Indexed
    private String traceId;
    @Indexed
    private String callSystemId;
    @Indexed
    private String appId;

    private JSONObject data;

    private Date createTime;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getCallSystemId() {
        return callSystemId;
    }

    public void setCallSystemId(String callSystemId) {
        this.callSystemId = callSystemId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
