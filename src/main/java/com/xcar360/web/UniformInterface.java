package com.xcar360.web;

import com.alibaba.fastjson.JSONObject;
import com.xcar360.action.AbstractRequestHandler;
import com.xcar360.util.ReturnCode;
import com.xcar360.util.UniformInterfaceUtils;
import com.xcar360.web.auth.AbstractRequest;
import com.xcar360.web.response.ResponseResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author xulaing
 * @date 2018年10月11日 14:44:55
 */
@RestController
@RequestMapping(value = "/inter")
public class UniformInterface {

    final protected static Logger logger = LoggerFactory.getLogger(UniformInterface.class);

    @RequestMapping( method= {RequestMethod.GET, RequestMethod.POST})
    public void work(HttpServletRequest request, HttpServletResponse response) throws Exception,ServletException, IOException{

        JSONObject params = UniformInterfaceUtils.getParams(request);
        AbstractRequest bizReq = null;
        ResponseResult bizResp = null;
        try {
            logger.info("uniform","接收到来自[host:{}({}:{})]的请求",
                    new Object[] { request.getRemoteHost(), request.getRemoteAddr(),
                            request.getRemotePort() });
            bizReq = AbstractRequest.fromHttpRequest(params);
        } catch (RuntimeException e) {
            if (e instanceof IllegalArgumentException) {
                logger.warn("#0000000000000000000000", "uniform",e.getMessage() + " \tallParams：{}", params);
            } else {
                logger.warn("#0000000000000000000000", "uniform","请求参数不合法，无法构建完整请求！allParams：{}", params, e);
            }
            bizResp = new ResponseResult("", ReturnCode.ERROR_PARAMS);
        }
        if (bizReq != null) {
            logger.debug("#0000000000000000000000", "uniform","解析到请求：{}", bizReq);
            bizResp = AbstractRequestHandler.handleRequest(bizReq, request.getServletContext());
        }
        logger.debug("#0000000000000000000000", "uniform","响应：{}", bizResp);

        Serializable data = (bizResp==null||bizResp.getData()==null)?null:(Serializable)bizResp.getData();
        if (data instanceof File) {
            File f = (File) data;
            InputStream in = new FileInputStream(f);
            try {
                responseDataStreamAndClose(response, in, (int) f.length(),
                        f.getName());
            } finally {
                FileUtils.deleteQuietly(f);
            }
        } else if (data instanceof byte[]) {
            byte[] bytes = (byte[]) data;
            InputStream in = new ByteArrayInputStream(bytes);
            responseDataStreamAndClose(response, in, bytes.length, "data");
        } else {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            if(bizResp==null){
                bizResp = new ResponseResult("#0000000000000000000000",ReturnCode.ERROR_UNKOWN_ERROR);
            }
            out.print(bizResp.toJsonString());
            out.flush();
            out.close();
        }
        logger.debug("#0000000000000000000000", "uniform","响应完成。");
    }

    private void responseDataStreamAndClose(HttpServletResponse response,
                                            InputStream in, int length, String fileName) throws IOException {
        if (StringUtils.endsWithIgnoreCase(fileName, ".pdf")) {
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition",
                    "inline;filename=" + URLEncoder.encode(fileName, "iso8859-1"));//iso8859-1
        } else {
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename="
                    + URLEncoder.encode(fileName, "iso8859-1"));
        }
        response.setContentLength(length);
        response.addHeader("Content-Length", String.valueOf(length));

        try {
            OutputStream out = response.getOutputStream();
            IOUtils.copy(in, out);
            out.flush();
            out.close();
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
