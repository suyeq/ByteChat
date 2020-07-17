package io.bytechat.processor.user;

import cn.hutool.core.bean.BeanUtil;
import io.bytechat.processor.request.AddFriendRequest;
import io.bytechat.server.session.SessionHelper;
import io.bytechat.server.session.SessionManager;
import io.bytechat.service.ImService;
import io.bytechat.service.UserService;
import io.bytechat.service.impl.DefaultUserService;
import io.bytechat.session.ImSession;
import io.bytechat.session.ImSessionManager;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PacketFactory;
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
 * @date : 16:46 2020/5/8
 */
@Slf4j
@Processor(name = ImService.FRIEND_ADD)
public class AddFriendProcessor extends AbstractRequestProcessor {

    private UserService userService;

    private SessionManager sessionManager;

    public AddFriendProcessor(){
        sessionManager = ImSessionManager.getInstance();
        userService = io.bytechat.utils.BeanUtil.getBean(DefaultUserService.class);
    }

    @Override
    public Payload doProcessor(ChannelHandlerContext channelHandlerContext, Map<String, Object> params) throws Exception {
        AddFriendRequest request = BeanUtil.mapToBean(params, AddFriendRequest.class, false);
        Long userOneId = request.getUserId();
        Channel channel = channelHandlerContext.channel();
        String sessionId = SessionHelper.getSessionId(channel);
        ImSession session = (ImSession) sessionManager.getSession(sessionId);
        Long userTwoId = session.userId();
        BaseResult result = userService.addFriend(userOneId, userTwoId);
        return result.isSuccess() ? PayloadFactory.newSuccessPayload() :
                PayloadFactory.newErrorPayload(400, result.getMsg());
    }
}
