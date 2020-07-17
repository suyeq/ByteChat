package io.bytechat.server;

import io.bytechat.lang.util.MultipleUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author : denglinhai
 * @date : 13:53 2020/2/26
 * 服务器的参数
 */
@Data
@Builder
@AllArgsConstructor
public class ServerAttr {

    private int port;

    private String address;

    private ServerMode serverMode;

    public static ServerAttr getLocalServer(int serverPort){
        return new ServerAttr(serverPort, MultipleUtil.getLocalIPV4(), ServerMode.STAND_ALONE);
    }

    public String routerKey(){
        return address + ":" + port;
    }
}
