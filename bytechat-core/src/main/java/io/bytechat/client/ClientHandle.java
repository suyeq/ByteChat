package io.bytechat.client;

import io.bytechat.tcp.ctx.RequestProcessorContext;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PacketFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : denglinhai
 * @date : 15:00 2020/3/17
 */
@Slf4j
public class ClientHandle extends ChannelInboundHandlerAdapter {

    private RequestProcessorContext requestProcessorContext;

    public ClientHandle(){
        requestProcessorContext = RequestProcessorContext.getInstance();
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
            System.out.println(packet);
            if (packet.getType() == Packet.PACKET_REQUEST){
                onRequest(ctx, packet);
            }else if (packet.getType() == Packet.PACKET_RESPONSE){
                onResponse(ctx, packet);
            }else {
                onCommand(ctx, packet);
            }
        }
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("[{}]通道关闭,原因{}", ctx.channel(), cause);
        ctx.close();
    }

    private void onCommand(ChannelHandlerContext ctx, Packet packet) {
        Payload payload = requestProcessorContext.process(ctx, packet.getRequest());
        Packet response = PacketFactory.newResponsePacket(payload, packet.getId());
        writeResponse(ctx, response);
    }

    private void onResponse(ChannelHandlerContext ctx, Packet packet) {

    }

    private void onRequest(ChannelHandlerContext ctx, Packet packet) {
    }

    private void writeResponse(ChannelHandlerContext ctx, Packet response) {
        if (response != null) {
            ctx.channel().writeAndFlush(response);
        }
    }
}
