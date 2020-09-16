package io.bytechat.processor.login;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import io.bytechat.entity.MessageEntity;
import io.bytechat.tcp.entity.Command;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.factory.CommandFactory;
import io.bytechat.tcp.factory.PacketFactory;
import io.bytechat.utils.BaseResult;
import io.bytechat.entity.UserEntity;
import io.bytechat.server.channel.ChannelHelper;
import io.bytechat.server.channel.ChannelType;
import io.bytechat.server.channel.ChannelWrapper;
import io.bytechat.server.session.SessionHelper;
import io.bytechat.server.session.SessionManager;
import io.bytechat.service.ImService;
import io.bytechat.session.ImSession;
import io.bytechat.session.ImSessionManager;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PayloadFactory;
import io.bytechat.tcp.processor.AbstractRequestProcessor;
import io.bytechat.tcp.processor.Processor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


/**
 * @author : denglinhai
 * @date : 15:50 2020/3/29
 * 登录消息处理器
 */
@Slf4j
@Processor(name = ImService.LOGIN)
public class LoginProcessor extends AbstractRequestProcessor {

    private SessionManager sessionManager;


    public LoginProcessor(){
        sessionManager = ImSessionManager.getInstance();
    }

    @Override
    public Payload doProcessor(ChannelHandlerContext channelHandlerContext, Map<String, Object> params) {
        LoginRequest loginRequest = BeanUtil.mapToBean(params, LoginRequest.class, false);
        //判断是否已经登录
        Channel channel = channelHandlerContext.channel();
        BaseResult userResult = BaseResult.newSuccessResult("200");
                //= userService.login(loginRequest.getUserName(), loginRequest.getPassword());
        if (userResult.isSuccess()){
            Random random = new Random();
            long userID = random.nextLong();
            System.out.println(userID);
            UserEntity user = new UserEntity().setId(userID);

                    //(UserEntity) userResult.getContent();
            ChannelWrapper channelWrapper = ChannelHelper.getChannelWrapper(channel.id());
            ChannelType channelType = channelWrapper.getChannelType();
            boolean isReadyLogin = sessionManager.exists(channelType, user.getId());
            if (isReadyLogin){
                return PayloadFactory.newErrorPayload(400, "该账号已经登录");
            }
            boundSession(channel, user);
            fetchP2pOffMsg(user, channelType);
            fetchGroupOffMsg(user, channelType);
        }
        Payload payload = userResult.isSuccess() ? PayloadFactory.newSuccessPayload()
                : PayloadFactory.newErrorPayload(userResult.getCode(), userResult.getMsg());
//        if (userResult.isSuccess()){
//            payload.setResult((UserEntity) userResult.getContent());
//        }
        return payload;
    }

    private void boundSession(Channel channel, UserEntity user) {
        ImSession imSession = (ImSession) sessionManager.newSession();
        imSession.setUserName(user.getUserName());
        imSession.setServerAddress("");
        imSession.setServerPort(1);
        sessionManager.bind(imSession, channel.id(), user.getId());
        SessionHelper.makeOnline(channel, imSession.sessionId());
        //广播上线消息
        //.....
    }

    /**
     * 拉取p2p离线消息
     * @param userEntity
     * @param channelType
     */
    private void fetchP2pOffMsg(UserEntity userEntity, ChannelType channelType){

    }

    private void fetchGroupOffMsg(UserEntity userEntity, ChannelType channelType){

    }

    /**
     * 构建传输packet 消息
     * @return
     */
    private Packet buildTransferPacketP2pMsg(MessageEntity messageEntity){
        Map<String, Object> params = new HashMap<>();
        params.put("userId", messageEntity.getSendUserId());
        params.put("userName", "offLineMsg");
        params.put("msgType", messageEntity.getMsgType());
        params.put("content", messageEntity.getContent());
        params.put("isGroup", messageEntity.getIsGroup());
        Command command = CommandFactory.newCommand(ImService.RECV_MSG, params);
        return PacketFactory.newCommandPacket(command);
    }

    /**
     * 构建传输packet 消息
     * @return
     */
    private Packet buildTransferPacketGroupMsg(MessageEntity messageEntity){
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", messageEntity.getGroupId());
        params.put("groupName", "offLineMsg");
        params.put("msgType", messageEntity.getMsgType());
        params.put("content", messageEntity.getContent());
        params.put("isGroup", messageEntity.getIsGroup());
        Command command = CommandFactory.newCommand(ImService.RECV_MSG, params);
        return PacketFactory.newCommandPacket(command);
    }
}
