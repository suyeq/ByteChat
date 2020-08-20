package io.bytechat.processor.msg;

import cn.hutool.core.bean.BeanUtil;
import io.bytechat.entity.GroupEntity;
import io.bytechat.entity.MessageEntity;
import io.bytechat.entity.UserEntity;
import io.bytechat.lang.id.IdFactory;
import io.bytechat.lang.id.SnowflakeIdFactory;
import io.bytechat.server.channel.ChannelType;
import io.bytechat.server.session.Session;
import io.bytechat.server.session.SessionHelper;
import io.bytechat.server.session.SessionManager;
import io.bytechat.service.ImService;
import io.bytechat.session.ImSessionManager;
import io.bytechat.tcp.entity.Command;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.CommandFactory;
import io.bytechat.tcp.factory.PacketFactory;
import io.bytechat.tcp.factory.PayloadFactory;
import io.bytechat.tcp.processor.AbstractRequestProcessor;
import io.bytechat.tcp.processor.Processor;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : denglinhai
 * @date : 20:31 2020/4/6
 */
@Slf4j
@Processor(name = ImService.GROUP_MSG)
public class GroupMsgProcessor extends AbstractRequestProcessor {

    private SessionManager sessionManager;

    private IdFactory idFactory;

    public GroupMsgProcessor(){
        sessionManager = ImSessionManager.getInstance();
        idFactory = SnowflakeIdFactory.getInstance();
    }

    @Override
    public Payload doProcessor(ChannelHandlerContext channelHandlerContext, Map<String, Object> params) {
        GroupMsgRequest request = BeanUtil.mapToBean(params, GroupMsgRequest.class, false);
        Long msgId = idFactory.nextId();
        Long sendUserId = SessionHelper.getUserId(channelHandlerContext.channel(), sessionManager);
        saveOfflineMsg(request, msgId, sendUserId);
        saveHistoryMsg(request, msgId, sendUserId);
        return PayloadFactory.newSuccessPayload();
    }

    /**
     * 构建传输packet 消息
     * @param request
     * @return
     */
    private Packet buildTransferPacketMsg(GroupMsgRequest request, GroupEntity groupEntity){
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", request.getGroupId());
        params.put("msgType", request.getMsgType());
        params.put("content", request.getContent());
        params.put("groupName", groupEntity.getGroupName());
        params.put("isGroup", 1);
        Command command = CommandFactory.newCommand(ImService.RECV_MSG, params);
        return PacketFactory.newCommandPacket(command);
    }

    /**
     * 存贮离线消息
     */
    private void saveOfflineMsg(GroupMsgRequest request, Long msgId, Long sendUserId) {

    }

    /**
     * 保存历史消息
     */
    private void saveHistoryMsg(GroupMsgRequest request, Long msgId, Long sendUserId){

    }

    private MessageEntity messageBuild(GroupMsgRequest request, Long msgId, Long sendUserId){
        MessageEntity message = MessageEntity.builder().messageId(msgId).groupId(request.getGroupId()).content(request.getContent())
                                .msgType(request.getMsgType()).sendTime(request.getSendTime()).isGroup(1).sendUserId(sendUserId).build();
        return message;
    }
}
