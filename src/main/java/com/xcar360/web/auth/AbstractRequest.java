package com.xcar360.web.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xcar360.util.UniformInterfaceUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
/**
 * @author xulaing
 * @date 2018年10月11日 14:44:55
 */
public abstract class AbstractRequest implements Serializable {

    private static final String DEFAULT_APP_KEY = "com.jzfq.rms";

    private static String currentApp;

    /**
     * @param currentApp the currentApp to set
     */
    public static void setCurrentApp(String currentApp) {
        AbstractRequest.currentApp = currentApp;
    }

    /**
     * @return the currentApp
     */
    public static String getCurrentApp() {
        if (StringUtils.isNotBlank(currentApp)) {
            return currentApp;
        }
        return DEFAULT_APP_KEY;
    }

    /**
     * 默认类路径
     */
    private static final String DEFAULT_REQ_BASE_PACKAGE = "com.xcar360.web.auth";

    private static String requsetPackage;


    /**
     *
     * @param requsetPackage
     */
    public static void setRequestPackage(String requsetPackage) {
        AbstractRequest.requsetPackage = requsetPackage;
    }

    private String appId;

    private long timestamp;

    private String apiId;

    private String token;

    private String apiVersion;

    private Map<String, Serializable> params = new HashMap<String, Serializable>();

    public AbstractRequest( String apiId, long timestamp
                          ) {
        this.appId = appId;
        this.apiId = apiId;
        this.timestamp = timestamp;
        this.token = token;
        this.apiVersion = apiVersion;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    /**
     * @return the params
     */
    public Map<String, Serializable> getParams() {
        Map<String, Serializable> tempParams = new HashMap<String, Serializable>(params.size());
        tempParams.putAll(params);
        return tempParams;
    }

    /**
     * @param params the params to set
     */
    public void setParams(Map<String, Serializable> params) {
        this.params.clear();
        if (params != null) {
            this.params.putAll(params);
        }
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void addParam(String name, Serializable value) {
        params.put(name, value);
    }

    public void removeParam(String name) {
        params.remove(name);
    }

    public Serializable getParam(String name) {
        return params.get(name);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String version) {
        this.apiVersion = version;
    }

    public static AbstractRequest fromHttpRequest(JSONObject params) throws RuntimeException {
        String apiId = params.getString(UniformInterfaceUtils.PARAM_NAME_APIID);
//        String appId = params.getString(UniformInterfaceUtils.PARAM_NAME_APPID);
        String timestampStr = params.getString(UniformInterfaceUtils.PARAM_NAME_TIMESTAMP);
//        String token = params.getString(UniformInterfaceUtils.PARAM_NAME_TOKEN);
//        String apiVersion = params.getString(UniformInterfaceUtils.PARAM_NAME_APIVERSION);

        Assert.hasText(apiId, "请求中缺少必要参数：" + UniformInterfaceUtils.PARAM_NAME_APIID);
//        Assert.hasText(appId, "请求中缺少必要参数：" + UniformInterfaceUtils.PARAM_NAME_APPID);
        Assert.hasText(timestampStr, "请求中缺少必要参数：" + UniformInterfaceUtils.PARAM_NAME_TIMESTAMP);
//        Assert.hasText(token, "请求中缺少必要参数：" + UniformInterfaceUtils.PARAM_NAME_TOKEN);
        Assert.isTrue(NumberUtils.isNumber(timestampStr), "参数" + UniformInterfaceUtils.PARAM_NAME_APIVERSION + "不是有效的数字");

        long timestamp = Long.parseLong(timestampStr);
        try {
            @SuppressWarnings("unchecked")
            Class<? extends AbstractRequest> clazz = (Class<? extends AbstractRequest>)
                    Class.forName(getRequestClassName(apiId));
            Constructor<? extends AbstractRequest> c = clazz.getConstructor(String.class, long.class);
            AbstractRequest req = c.newInstance(apiId, timestamp);
            req.getBizParams(params);
            return req;
        } catch (Exception e) {
            throw new RuntimeException("根据请求构建请求实体类出错", e);
        }
    }

    /**
     *
     * @param apiId
     * @return
     */
    private static String getRequestClassName(String apiId) {
        if (requsetPackage==null|| StringUtils.startsWith(apiId, "S")) {
            requsetPackage = DEFAULT_REQ_BASE_PACKAGE;
        }
        return requsetPackage + ".Request" + apiId;
    }

    /**
     *
     * @param params
     */
    protected abstract void getBizParams(JSONObject params);

    /**
     *
     * @param params
     * @param paramNames
     */
    protected final void getSimpleStringParams(JSONObject params, String... paramNames) {
        if (paramNames == null) {
            return;
        }
        for (String paramName : paramNames) {
            String param = params.getString(paramName);
            addParam(paramName, param == null ? null : param.trim());
        }
    }

//    /**
//     * 验证签名合法性
//     * @return 合法返回true，否则返回false
//     */
//    public final boolean verifyToken() {
//        String appSecret = KeyManager.STR_APPSECRET;
//        if (appSecret == null) {
//            return false;
//        }
//
//        if (Math.abs(System.currentTimeMillis() - timestamp) > KeyManager.MILLIS_PER_SECOND) {
//            return false;
//        }
//
//        String tempToken = UniformInterfaceUtils.getToken(appId, apiId, appSecret,timestamp);
//        return StringUtils.equalsIgnoreCase(tempToken, token);
//    }

}