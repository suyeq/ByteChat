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
        return request;
    }
}
