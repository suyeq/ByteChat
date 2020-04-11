package io.bytechat.processor.login;

import cn.hutool.core.bean.BeanUtil;
import io.bytechat.utils.BaseResult;
import io.bytechat.entity.UserEntity;
import io.bytechat.server.channel.ChannelHelper;
import io.bytechat.server.channel.ChannelType;
import io.bytechat.server.channel.ChannelWrapper;
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
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


/**
 * @author : denglinhai
 * @date : 15:50 2020/3/29
 * 登录消息处理器
 */
@Slf4j
@Processor(name = ImService.LOGIN)
public class LoginProcessor extends AbstractRequestProcessor {

    private SessionManager sessionManager;

    private UserService userService;

    public LoginProcessor(){
        sessionManager = ImSessionManager.getInstance();
        userService = io.bytechat.utils.BeanUtil.getBean(DefaultUserService.class);
    }

    @Override
    public Payload doProcessor(ChannelHandlerContext channelHandlerContext, Map<String, Object> params) {
        LoginRequest loginRequest = BeanUtil.mapToBean(params, LoginRequest.class, false);
        //判断是否已经登录
        Channel channel = channelHandlerContext.channel();
        BaseResult userResult = userService.login(loginRequest.getUserName(), loginRequest.getPassword());
        if (userResult.isSuccess()){
            UserEntity user = (UserEntity) userResult.getContent();
            ChannelWrapper channelWrapper = ChannelHelper.getChannelWrapper(channel.id());
            ChannelType channelType = channelWrapper.getChannelType();
            boolean isReadyLogin = sessionManager.exists(channelType, user.getId());
            if (isReadyLogin){
                return PayloadFactory.newErrorPayload(400, "该账号已经登录");
            }
            boundSession(channel, user);
        }
        return userResult.isSuccess() ? PayloadFactory.newSuccessPayload()
                : PayloadFactory.newErrorPayload(userResult.getCode(), userResult.getMsg());
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
}
