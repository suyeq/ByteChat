package io.bytechat.server.channel;


import java.util.Arrays;

/**
 * @author : denglinhai
 * @date : 15:46 2020/2/26
 * channel类型
 */
public enum ChannelType {

    /**
     * 自定义tcp上的消息协议
     */
    TCP(1),

    /**
     * 采用websocket协议传输
     */
    WEBSOCKET(2);

    private int type;

    ChannelType(int type){
        this.type = type;
    }

    public static ChannelType getChannelType(Integer type){
        return Arrays.stream(values())
                .filter(e -> e.type == type).findFirst().orElse(null);
    }
}
