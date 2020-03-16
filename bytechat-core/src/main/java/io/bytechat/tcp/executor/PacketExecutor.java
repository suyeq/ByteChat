package io.bytechat.tcp.executor;

import cn.hutool.core.lang.Singleton;
import io.bytechat.executor.AbstractExecutor;
import io.bytechat.server.AbstractServer;
import io.bytechat.tcp.ctx.RequestProcessorContext;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PacketFactory;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author : denglinhai
 * @date : 15:20 2020/3/16
 */
public class PacketExecutor extends AbstractExecutor<Packet> {

    private RequestProcessorContext requestHandler;

    private PacketExecutor() {
        this.requestHandler = RequestProcessorContext.getInstance();
    }

    public static PacketExecutor newInstance() {
        return Singleton.get(PacketExecutor.class);
    }

    @Override
    public Packet doExecute(Object... request) {
        ChannelHandlerContext ctx = (ChannelHandlerContext) request[0];
        Packet packet = (Packet) request[1];
        Payload payload = requestHandler.process(ctx, packet.getRequest());
        return PacketFactory.newResponsePacket(payload, packet.getId());
    }
}
