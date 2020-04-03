package io.bytechat.processor.msg;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import io.bytechat.lang.id.IdFactory;
import io.bytechat.lang.id.MemoryIdFactory;
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
 */
@Slf4j
@Processor(name = ImService.P2P_MSG)
public class SendP2pMsgProcessor extends AbstractRequestProcessor {

    private IdFactory idFactory;

    private SessionManager sessionManager;

    public SendP2pMsgProcessor(){
        idFactory = MemoryIdFactory.newInstance();
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
        ChannelType channelType = ChannelType.getChannelType( toChannelType == null ? 0 : toChannelType);
        ImSession toSession =(ImSession) sessionManager.getSessionByUserIdAndChannelType(toUserId, channelType);
        if (ObjectUtil.isNull(toSession)) {
            log.info("{}不在线，存贮离线消息", toSession.userId());
            saveOfflineMsg();
        }else {
            if (toSession.channelType() == ChannelType.TCP){
                Packet packet = buildTransferPacketMsg(toUserId, "", null);
            }else {

            }
            saveHistoryMsg();
        }
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
        params.put("channelType", request.getChannelType());
        params.put("content", request.getContent());
        Command command = CommandFactory.newCommand("", params);
        return PacketFactory.newCommandPacket(command);
    }

    private void saveOfflineMsg() {

    }

    private void saveHistoryMsg(){

    }
}
