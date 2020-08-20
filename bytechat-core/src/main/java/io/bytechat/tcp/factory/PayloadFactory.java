package io.bytechat.tcp.factory;

import io.bytechat.tcp.entity.Payload;

/**
 * @author : denglinhai
 * @date : 15:12 2020/2/29
 */
public class PayloadFactory {

    public static Payload newSuccessPayload(){
        Payload payload = new Payload();
        payload.setSuccessMsg(200, "success");
        payload.setType(Payload.PAYLOAD_MSG);
        return payload;
    }

    public static Payload newSuccessAckPayload(){
        Payload payload = new Payload();
        payload.setSuccessMsg(200, "success");
        payload.setType(Payload.PAYLOAD_ACK);
        return payload;
    }

    public static Payload newSuccessPayload(Object object){
        Payload payload = new Payload();
        payload.setSuccessMsg(200, "success");
        payload.setResult(object);
        return payload;
    }

    public static  Payload newErrorPayload(int code, String msg){
        Payload payload = new Payload();
        payload.setErrorMsg(code, msg);
        return payload;
    }
}
