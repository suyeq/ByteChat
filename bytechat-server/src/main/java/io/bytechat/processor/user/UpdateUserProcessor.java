package io.bytechat.processor.user;

import cn.hutool.core.bean.BeanUtil;
import io.bytechat.processor.login.LoginRequest;
import io.bytechat.server.session.SessionManager;
import io.bytechat.service.UserService;
import io.bytechat.service.impl.DefaultUserService;
import io.bytechat.session.ImSessionManager;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PayloadFactory;
import io.bytechat.tcp.processor.AbstractRequestProcessor;
import io.bytechat.tcp.processor.Processor;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

/**
 * @author : denglinhai
 * @date :
 */
@Processor(name = "update_user")
public class UpdateUserProcessor extends AbstractRequestProcessor {

    private UserService userService;

    private SessionManager sessionManager;

    public UpdateUserProcessor(){
        sessionManager = ImSessionManager.getInstance();
        userService = io.bytechat.utils.BeanUtil.getBean(DefaultUserService.class);
    }


    @Override
    public Payload doProcessor(ChannelHandlerContext channelHandlerContext, Map<String, Object> params) throws Exception {
        LoginRequest loginRequest = BeanUtil.mapToBean(params, LoginRequest.class, false);
        updateUser(loginRequest);
        return PayloadFactory.newSuccessPayload();
    }

    private void updateUser(LoginRequest loginRequest) {
        userService.update(loginRequest);
    }
}
