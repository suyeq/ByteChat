package io.bytechat.client;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.factory.PacketFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author : denglinhai
 * @date : 18:43 2020/3/19
 */
@Slf4j
public class HealthyChecker extends ChannelInboundHandlerAdapter {

    private Client client;

    private int pingInterval;

    private volatile int reConnect;

    private final int DEFAULT_RE_CONNECT = 3;

    private final int DEFAULT_PING_INTERVAL = 4;

    public HealthyChecker(int pingInterval, int reConnect, Client client){
        this.client = client;
        this.reConnect = reConnect <=0 ? DEFAULT_RE_CONNECT : reConnect;
        this.pingInterval = pingInterval <= 0 ? DEFAULT_PING_INTERVAL : pingInterval;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        schedulePing(ctx);
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.executor().schedule(new Runnable() {
            @Override
            public void run() {
                if (reConnect > 0){
                    log.info("[{}]尝试第{}次重连", HealthyChecker.class.getSimpleName(), 4 - reConnect);
                    client.connect();
                    reConnect--;
                }
            }
        }, 5, TimeUnit.SECONDS);
        ctx.fireChannelInactive();
    }

    private void schedulePing(ChannelHandlerContext ctx){
        ctx.executor().schedule(new Runnable() {
            @Override
            public void run() {
                Channel channel = ctx.channel();
                if (channel.isActive()){
                    Packet pingPacket = PacketFactory.newPingRequestPacket();
                    channel.writeAndFlush(pingPacket);
                    log.info("[{}] 发送了一个ping消息{}", HealthyChecker.class.getSimpleName(), pingPacket);
                    schedulePing(ctx);
                }
            }
        }, pingInterval, TimeUnit.SECONDS);
    }
}
