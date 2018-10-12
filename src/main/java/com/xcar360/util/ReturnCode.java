package com.xcar360.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xulaing
 * @date 2018年8月08日 20:44:55
 */
public enum ReturnCode {
    ERROR_REFUSE_IP(-60,"拒绝IP"),
    NOT_EXIST_SEND_HANDLER(-2, "不存在发送处理模块"),
    ACTIVE_EXCEPTION(-1, "异常"),
    ACTIVE_SUCCESS(01, "操作成功"),
    ACTIVE_FAILURE(02, "操作失败（接口ping不通）"),
    ERROR_PARAMS_NOT_NULL(03, "参数不能为空"),
    ERROR_HEADER_NOT_NULL(04, "请求头不能为空"),
    ERROR_INVALID_TOKEN(05, "请求token验证不通过"),
    ERROR_UNKNOWN_API(06, "功能接口不存在"),
    ERROR_INVALID_ARGS(07, "请求参数不合法"),
    ERROR_SYSTEM_CONFIG_NULL(8,"系统配置出错"),
    ERROR_RESPONSE_NULL(9,"响应为空"),
    ERROR_THIRD_RESPONSE(10,"第三方接口错误"),
    ERROR_THIRD_RRSPONSE_NULL(11,"第三方返回为空"),
    ERROR_THIRD_RRSPONSE_NON_JSON(12,"d第三方返回一个空的json"),
    ERROR_TASK_ID_NULL(13,"根据订单号获取taskId失败"),
    ERROR_NOT_FOUNT_EVENT_ID(14,"获取不到eventId"),
    ERROR_NOT_FOUNT_STRATEGE_ID(15,"获取不到strategyId"),
    ACTIVE_THIRD_RPC(100,"远程调用接口中"),
    REQUEST_THIRD_GETING(150,"数据抓取中"),
    REQUEST_SAME_EXCUTING(199,"有相同的请求正在执行"),
    REQUEST_SUCCESS(200,"请求成功"),
    ERROR_PARAMS(400, "参数不完整"),
    ERROR_DUPLICATE(401, "重复操作"),
    ERROR_AUTH(402, "无权限"),
    ERROR_PARAMS_DECRYPT(402, "参数解密失败"),
    ERROR_WRONG(403, "用户无法使用此系统"),
    ERROR_RESOURCES(404, "请求资源不存在"),
    ERROR_PARAMS_FORMAT(500, "参数格式错误"),
    ERROR_SERVER(503, "系统异常"),
    ERROR_USER_TYPE_ERROR(1111, "用户类型参数错误"),
    ERROR_RSLL_PARAMS_ERROR(2222, "调用融360接口，返回参数RSL发生错误"),
    REQUEST_NO_EXIST_DATA(200,"此数据mongodb不存在，请删除缓存重新拉取"),
    REQUEST_NO_PY_URL(500,"鹏元获取车辆信息 接口URL为null"),
    ERROR_UNKOWN_ERROR(9999, "未知错误,响应数据为null");
    private int code;

    private String msg;

    private ReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ReturnCode codeToEnum(int code) {

        ReturnCode[] values = ReturnCode.values();
        for (ReturnCode returnCode : values) {
            if (returnCode.code == code) {
                return returnCode;
            }
        }
        return ACTIVE_EXCEPTION;
    }

    public int code() {
        return code;
    }


    public String msg() {
        return msg;
    }


    public Map<String, ?> Map() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("code", this.code);
        hashMap.put("msg", this.msg);
        return hashMap;
    }

    public Map<String, ?> Map(int code) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("code", code);
        hashMap.put("msg", this.msg);
        return hashMap;
    }

    public Map<String, ?> Map(Object msg) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("code", this.code);
        hashMap.put("msg", msg);
        return hashMap;
    }

    public Map<String, ?> Map(int code, Object msg) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("code", code);
        hashMap.put("msg", msg);
        return hashMap;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("{\"code\":").append(this.code).append(",");
        sb.append("\"msg\":\"").append(this.msg).append("\"}");

        return sb.toString();
    }


}
