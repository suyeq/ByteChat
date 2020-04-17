package io.bytechat.interceptor;

import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : denglinhai
 * @date : 19:53 2020/4/17
 */
@Slf4j
@Interceptor(name = "login")
public class LoginInterceptor extends AbstractInterceptor{

    @Override
    public Payload preHandle(Packet packet, Channel channel) {
        return null;
    }
}
