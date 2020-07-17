package io.bytechat;

import io.bytechat.client.Client;
import io.bytechat.func.*;
import io.bytechat.tcp.entity.Payload;

/**
 * @author : denglinhai
 * @date : 16:32 2020/4/19
 */
public class ClientBootstrap {

    private BaseFunc baseFunc;

    private UserFunc userFunc;

    private LoginFunc loginFunc;

    private SendP2pFunc sendP2pFunc;

    private RegisterFunc registerFunc;

    private SendGroupMsgFunc sendGroupMsgFunc;


    public ClientBootstrap(Client client){
        this.baseFunc = new BaseFunc(client);
        this.userFunc = new UserFunc(baseFunc);
        this.loginFunc = new LoginFunc(baseFunc);
        this.sendP2pFunc = new SendP2pFunc(baseFunc);
        this.registerFunc = new RegisterFunc(baseFunc);
        this.sendGroupMsgFunc = new SendGroupMsgFunc(baseFunc);
    }

    public Payload groupChat(Long groupId, String message, Integer channelType, Byte msgType){
        return sendGroupMsgFunc.sendGroupMsg(groupId, message, channelType, msgType);
    }

    public Payload p2pChat(Long userId, String message, Integer channelType, Byte msgType){
        return sendP2pFunc.sendP2pMsg(userId, channelType, message, msgType);
    }

    public Payload login(String userName, String password){
        return loginFunc.login(userName, password);
    }

    public Payload register(String userName, String password){
        return registerFunc.register(userName, password);
    }
}
