package io.bytechat.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PayloadFactory;
import io.netty.channel.Channel;

import java.util.List;

/**
 * @author : denglinhai
 * @date : 16:27 2020/4/17
 */
public class InterceptorHandle {

    public static Payload preHandle(Channel channel, Packet packet){
        List<AbstractInterceptor> interceptors = InterceptorContext.getInterceptors();
        if (CollectionUtil.isEmpty(interceptors)){
            return PayloadFactory.newSuccessPayload();
        }
        for (AbstractInterceptor interceptor : interceptors){
            Payload payload = interceptor.preHandle(packet, channel);
            if (!payload.isSuccess()){
                return payload;
            }
        }
        return PayloadFactory.newSuccessPayload();
    }
}
