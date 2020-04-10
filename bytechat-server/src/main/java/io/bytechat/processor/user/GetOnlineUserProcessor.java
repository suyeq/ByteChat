package io.bytechat.processor.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import io.bytechat.entity.UserEntity;
import io.bytechat.server.session.Session;
import io.bytechat.server.session.SessionHelper;
import io.bytechat.server.session.SessionManager;
import io.bytechat.service.ImService;
import io.bytechat.service.UserService;
import io.bytechat.service.impl.DefaultUserService;
import io.bytechat.session.ImSession;
import io.bytechat.session.ImSessionManager;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PayloadFactory;
import io.bytechat.tcp.processor.AbstractRequestProcessor;
import io.bytechat.tcp.processor.Processor;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : denglinhai
 * @date : 16:25 2020/4/9
 */
@Slf4j
@Processor(name = ImService.GET_ONLINE_USER)
public class GetOnlineUserProcessor extends AbstractRequestProcessor {

    private UserService userService;

    private SessionManager sessionManager;

    public GetOnlineUserProcessor(){
        this.userService = io.bytechat.utils.BeanUtil.getBean(DefaultUserService.class);
        this.sessionManager = ImSessionManager.getInstance();
    }

    @Override
    public Payload doProcessor(ChannelHandlerContext channelHandlerContext, Map<String, Object> params) {
        String sessionId = SessionHelper.getSessionId(channelHandlerContext.channel());
        ImSession session = (ImSession) sessionManager.getSession(sessionId);
        Long currentUserId = session.userId();
        List<Session> sessions = sessionManager.fetchAllSession();
        List<UserEntity> userEntities = sessions.stream()
                .filter(e -> e.userId() != currentUserId)
                .map(e -> {
                    ImSession imSession = (ImSession) e;
                    return UserEntity.builder().userId(imSession.userId())
                            .userName(imSession.getUserName()).build();
                }).collect(Collectors.toList());
        Payload payload = PayloadFactory.newSuccessPayload();
        if (CollectionUtil.isNotEmpty(userEntities)){
            payload.setResult(userEntities);
        }
        return payload;
    }
}
