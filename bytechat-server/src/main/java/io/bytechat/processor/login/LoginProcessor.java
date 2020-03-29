package io.bytechat.processor.login;

import cn.hutool.core.bean.BeanUtil;
import io.bytechat.entity.User;
import io.bytechat.server.channel.ChannelHelper;
import io.bytechat.server.channel.ChannelType;
import io.bytechat.server.channel.ChannelWrapper;
import io.bytechat.server.session.DefaultSessionManager;
import io.bytechat.server.session.SessionManager;
import io.bytechat.service.ImService;
import io.bytechat.session.ImSession;
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
 * @date : 15:50 2020/3/29
 */
@Slf4j
@Processor(name = ImService.LOGIN)
public class LoginProcessor extends AbstractRequestProcessor {

    private SessionManager sessionManager;

    public LoginProcessor(){
        sessionManager = DefaultSessionManager.newInstance();
    }

    @Override
    public Payload doProcessor(ChannelHandlerContext channelHandlerContext, Map<String, Object> params) {
        LoginRequest loginRequest = BeanUtil.mapToBean(params, LoginRequest.class, false);
        //判断是否已经登录
        Channel channel = channelHandlerContext.channel();
        User user = new User();
        if (true){
            ChannelWrapper channelWrapper = ChannelHelper.getChannelWrapper(channel.id());
            ChannelType channelType = channelWrapper.getChannelType();
            boolean isReadyLogin = sessionManager.exists(channelType, user.getUserId());
            if (isReadyLogin){
                return PayloadFactory.newErrorPayload(400, "该账号已经登录");
            }
            boundSession(channel, user);
        }
        return PayloadFactory.newSuccessPayload();
    }

    private void boundSession(Channel channel, User user) {
        ImSession imSession = (ImSession) sessionManager.newSession();
        sessionManager.bind(imSession, channel.id(), user.getUserId());
        //广播上线消息
        //.....
    }
}
