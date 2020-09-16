package io.bytechat.lang.constant;

/**
 * @author : denglinhai
 * @date : 14:47 2020/9/16
 * 参数枚举类
 */
public enum ParamEnums {

    /** 消息包id **/
    PACKET_ID("packetId"),
    SEND_USER_ID("sendUserId"),
    RECV_USER_ID("recvUserId");

    private String code;

    ParamEnums(String code){
        this.code = code;
    }

    @Override
    public String toString(){
        return code;
    }
}
