package io.bytechat.tcp.factory;

import io.bytechat.tcp.entity.Request;

import java.util.Map;

/**
 * @author : denglinhai
 * @date : 15:08 2020/2/29
 */
public class RequestFactory {

    public static Request newRequest(String serviceName, String methodName, Map<String, Object> params){
        Request request = new Request();
        request.setServiceName(serviceName);
        request.setMethodName(methodName);
        request.setParams(params);
        request.setType(Request.REQUEST_MSG);
        return request;
    }

    public static Request newRequest(){
        Request request = new Request();
        request.setType(Request.REQUEST_ACK);
        return request;
    }

    public static Request newRequest(String serviceName, Map<String, Object> params){
        Request request = new Request();
        request.setParams(params);
        request.setServiceName(serviceName);
        request.setType(Request.REQUEST_MSG);
        return request;
    }
}
