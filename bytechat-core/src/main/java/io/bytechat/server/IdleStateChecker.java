package io.bytechat.server;

import io.bytechat.lang.config.BaseConfig;
import io.bytechat.lang.config.ConfigFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author : denglinhai
 * @date : 17:32 2020/3/19
 * 心跳检测
 */
@Slf4j
public class IdleStateChecker extends IdleStateHandler {

    private int idlReadTime;

    private final static int DEFAULT_IDLE_READ_TIME = 4;

    public IdleStateChecker(Integer idlReadTime) {
        super(idlReadTime == null ? DEFAULT_IDLE_READ_TIME : idlReadTime, 0, 0, TimeUnit.SECONDS);
        this.idlReadTime = idlReadTime;
    }

    @Override
    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt){
        log.info("服务器在{}s内未收到来自{}的消息，关闭该连接", idlReadTime, ctx.channel());
        ctx.channel().close();
    }

}
