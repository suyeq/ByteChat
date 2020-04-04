package io.bytechat.client;

import io.bytechat.tcp.ctx.CommandProcessorContext;
import io.bytechat.tcp.ctx.RequestProcessorContext;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PacketFactory;
import io.bytechat.tcp.factory.PendingPackets;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * @author : denglinhai
 * @date : 15:00 2020/3/17
 */
@Slf4j
public class ClientHandle extends ChannelInboundHandlerAdapter {

    private RequestProcessorContext requestProcessorContext;

    private CommandProcessorContext commandProcessorContext;

    public ClientHandle(){
        requestProcessorContext = RequestProcessorContext.getInstance();
        commandProcessorContext = CommandProcessorContext.getInstance();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Packet){
            Packet packet = (Packet) msg;
            if (packet.getType() == Packet.PACKET_REQUEST){
                onRequest(ctx, packet);
            }else if (packet.getType() == Packet.PACKET_RESPONSE){
                onResponse(ctx, packet);
            }else {
                onCommand(ctx, packet);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("[{}]通道关闭,原因{}", ctx.channel(), cause);
        ctx.close();
    }

    private void onCommand(ChannelHandlerContext ctx, Packet packet) {
        commandProcessorContext.process(ctx, packet.getCommand());
    }

    private void onResponse(ChannelHandlerContext ctx, Packet packet) {
        CompletableFuture<Packet> pending = PendingPackets.remove(packet.getId());
        if (pending != null) {
            pending.complete(packet);
        }
    }

    private void onRequest(ChannelHandlerContext ctx, Packet packet) {
        Payload payload = requestProcessorContext.process(ctx, packet.getRequest());
        Packet response = PacketFactory.newResponsePacket(payload, packet.getId());
        writeResponse(ctx, response);
    }

    private void writeResponse(ChannelHandlerContext ctx, Packet response) {
        if (response != null) {
            ctx.channel().writeAndFlush(response);
        }
    }
}
