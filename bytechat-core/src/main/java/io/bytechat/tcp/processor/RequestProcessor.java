package io.bytechat.tcp.processor;

import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.entity.Request;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author : denglinhai
 * @date : 19:20 2020/3/2
 * 请求业务处理器
 */
public interface RequestProcessor {

    /**
     * 自定义实现处理器
     * @param channelHandlerContext
     * @param request
     * @return
     */
    Payload process(ChannelHandlerContext channelHandlerContext, Request request);
}
