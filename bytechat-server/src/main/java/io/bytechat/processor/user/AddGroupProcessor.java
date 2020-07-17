package io.bytechat.processor.user;

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
import io.bytechat.utils.BeanUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author : denglinhai
 * @date : 18:12 2020/5/8
 */
@Slf4j
@Processor(name = ImService.GROUP_ADD)
public class AddGroupProcessor extends AbstractRequestProcessor {

    private GroupService groupService;

    private SessionManager sessionManager;

    public AddGroupProcessor(){
        sessionManager = ImSessionManager.getInstance();
        groupService = BeanUtil.getBean(DefaultGroupService.class);
    }

    @Override
    public Payload doProcessor(ChannelHandlerContext channelHandlerContext, Map<String, Object> params) throws Exception {
        Channel channel = channelHandlerContext.channel();
        String sessionId = SessionHelper.getSessionId(channel);
        ImSession session = (ImSession) sessionManager.getSession(sessionId);
        Long userId = session.userId();
        String groupName = session.getUserName() + "的群";
        BaseResult createGroup = groupService.createGroup(userId, groupName);
        Long groupId = (Long) createGroup.getContent();
        groupService.joinGroup(userId, groupId);
        return createGroup.isSuccess() ? PayloadFactory.newSuccessPayload(createGroup.getContent()) :
                PayloadFactory.newErrorPayload(400, createGroup.getMsg());
    }
}
