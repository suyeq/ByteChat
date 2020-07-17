package io.bytechat.processor.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import io.bytechat.processor.request.UserOfflineRequest;
import io.bytechat.server.channel.ChannelHelper;
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

import java.util.Map;

/**
 * @author : denglinhai
 * @date : 12:56 2020/5/7
 */
@Slf4j
@Processor(name = ImService.USER_OFFLINE)
public class UserOfflineProcessor extends AbstractRequestProcessor {

    private SessionManager sessionManager;

    public UserOfflineProcessor(){
        this.sessionManager = ImSessionManager.getInstance();
    }

    @Override
    public Payload doProcessor(ChannelHandlerContext channelHandlerContext, Map<String, Object> params) throws Exception {
        UserOfflineRequest request = BeanUtil.mapToBean(params, UserOfflineRequest.class, false);
        long userId = request.getUserId();
        ImSession session =(ImSession) sessionManager.getSession(userId);
        if (ObjectUtil.isNull(session)){
            return PayloadFactory.newErrorPayload(400, "已下线");
        }
        Channel channel = ChannelHelper.getChannelWrapper(session.channelId()).getChannel();
        SessionHelper.makeOffline(channel, session.sessionId());
        //channel.close();
        return PayloadFactory.newSuccessPayload();
    }
}
