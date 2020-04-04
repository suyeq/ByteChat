package io.bytechat.tcp.processor;

import io.bytechat.tcp.entity.Command;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author : denglinhai
 * @date : 14:22 2020/4/4
 */
public interface CommandProcessor {

    /**
     * 自定义命令处理器
     * @param ctx
     * @param command
     */
    void process(ChannelHandlerContext ctx, Command command);
}
