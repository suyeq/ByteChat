package io.bytechat.processor.msg;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import io.bytechat.entity.MessageEntity;
import io.bytechat.lang.id.IdFactory;
import io.bytechat.lang.id.SnowflakeIdFactory;
import io.bytechat.server.channel.ChannelType;
import io.bytechat.server.session.SessionHelper;
import io.bytechat.server.session.SessionManager;
import io.bytechat.service.ImService;
import io.bytechat.session.ImSession;
import io.bytechat.session.ImSessionManager;
import io.bytechat.tcp.entity.Command;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.CommandFactory;
import io.bytechat.tcp.factory.PacketFactory;
import io.bytechat.tcp.factory.PayloadFactory;
import io.bytechat.tcp.processor.AbstractRequestProcessor;
import io.bytechat.tcp.processor.Processor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : denglinhai
 * @date : 15:12 2020/4/3
 * 发送单点消息
 */
@Slf4j
@Processor(name = ImService.P2P_MSG)
public class SendP2pMsgProcessor extends AbstractRequestProcessor {

    private IdFactory idFactory;

    private SessionManager sessionManager;

    public SendP2pMsgProcessor(){
        idFactory = SnowflakeIdFactory.getInstance();
        sessionManager = ImSessionManager.getInstance();
    }

    @Override
    public Payload doProcessor(ChannelHandlerContext channelHandlerContext, Map<String, Object> params) {
        SendP2pMsgRequest request = BeanUtil.mapToBean(params, SendP2pMsgRequest.class, false);
        Channel fromChannel = channelHandlerContext.channel();
        String sessionId = SessionHelper.getSessionId(fromChannel);
        ImSession session =(ImSession) sessionManager.getSession(sessionId);
        long fromUserId = session.userId();
        String fromUserName = session.getUserName();
        long toUserId = request.getToUserId();
        Integer toChannelType = request.getChannelType();
        Long msgId = idFactory.nextId();
        ChannelType channelType = ChannelType.getChannelType( toChannelType == null ? 0 : toChannelType);
        ImSession toSession =(ImSession) sessionManager.fetchSessionByUserIdAndChannelType(toUserId, channelType);

        if (ObjectUtil.isNull(toSession)) {
            log.info("{}不在线，存贮离线消息", toUserId);
            saveOfflineMsg(fromUserId, request, msgId);
        }else {
            Object transferMsg;
            if (toSession.channelType() == ChannelType.TCP){
                transferMsg = buildTransferPacketMsg(fromUserId, fromUserName, request);
            }else {
                transferMsg = null;
            }
            toSession.writeAndFlush(transferMsg);
        }
        saveHistoryMsg(fromUserId, request, msgId);
        return PayloadFactory.newSuccessPayload();
    }

    /**
     * 构建传输packet 消息
     * @param userId
     * @param userName
     * @param request
     * @return
     */
    private Packet buildTransferPacketMsg(long userId, String userName, SendP2pMsgRequest request){
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("userName", userName);
        params.put("msgType", request.getMsgType());
        params.put("content", request.getContent());
        params.put("isGroup", 0);
        Command command = CommandFactory.newCommand(ImService.RECV_MSG, params);
        return PacketFactory.newCommandPacket(command);
    }

    /**
     * 存贮离线消息
     */
    private void saveOfflineMsg(Long userId, SendP2pMsgRequest request, Long msgId) {

    }

    /**
     * 保存历史消息
     */
    private void saveHistoryMsg(Long userId, SendP2pMsgRequest request, Long msgId){

    }

    private MessageEntity messageBuild(Long userId, SendP2pMsgRequest request, Long msgId){
        MessageEntity message = MessageEntity.builder().messageId(msgId).sendUserId(userId)
                                .recvUserId(request.getToUserId()).content(request.getContent())
                                .msgType(request.getMsgType()).sendTime(request.getSendTime())
                                .isGroup(0).build();
        return message;
    }
}
