package io.bytechat.processor.user;

import cn.hutool.core.bean.BeanUtil;
import io.bytechat.processor.request.JoinGroupRequest;
import io.bytechat.server.session.SessionHelper;
import io.bytechat.server.session.SessionManager;
import io.bytechat.service.GroupService;
import io.bytechat.service.ImService;
import io.bytechat.service.impl.DefaultGroupService;
import io.bytechat.session.ImSession;
import io.bytechat.session.ImSessionManager;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PayloadFactory;
import io.bytechat.tcp.processor.AbstractRequestProcessor;
import io.bytechat.tcp.processor.Processor;
import io.bytechat.utils.BaseResult;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author : denglinhai
 * @date : 20:54 2020/5/8
 */
@Slf4j
@Processor(name = ImService.GROUP_JOIN)
public class JoinGroupProcessor extends AbstractRequestProcessor {

    private GroupService groupService;

    private SessionManager sessionManager;

    public JoinGroupProcessor(){
        sessionManager = ImSessionManager.getInstance();
        groupService = io.bytechat.utils.BeanUtil.getBean(DefaultGroupService.class);
    }

    @Override
    public Payload doProcessor(ChannelHandlerContext channelHandlerContext, Map<String, Object> params) throws Exception {
        JoinGroupRequest request = BeanUtil.mapToBean(params, JoinGroupRequest.class, false);
        Long groupId = request.getGroupId();
        Channel channel = channelHandlerContext.channel();
        String sessionId = SessionHelper.getSessionId(channel);
        ImSession session = (ImSession) sessionManager.getSession(sessionId);
        Long userId = session.userId();
        BaseResult joinResult = groupService.joinGroup(userId, groupId);
        return joinResult.isSuccess() ? PayloadFactory.newSuccessPayload() :
                PayloadFactory.newErrorPayload(400, joinResult.getMsg());
    }
}
