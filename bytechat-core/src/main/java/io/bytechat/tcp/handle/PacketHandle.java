package io.bytechat.tcp.handle;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.ObjectUtil;
import io.bytechat.executor.Executor;
import io.bytechat.server.channel.ChannelListener;
import io.bytechat.server.channel.ChannelType;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.executor.PacketExecutor;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : denglinhai
 * @date : 18:50 2020/3/16
 */
@Slf4j
@ChannelHandler.Sharable
public class PacketHandle extends ChannelInboundHandlerAdapter{

    private Executor<Packet> executor;

    private ChannelListener channelListener;

    private PacketHandle(){

    }

    private PacketHandle(ChannelListener channelListener){
        Assert.notNull(channelListener, "channelListener不能为空");
        this.channelListener = channelListener;
        this.executor = PacketExecutor.newInstance();
    }

    public static PacketHandle newInstance(ChannelListener channelListener){
        return Singleton.get(PacketHandle.class, channelListener);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelListener.channelActive(ctx.channel(), ChannelType.TCP);
        log.info("[{}] channel 被激活", ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channelListener.channelInactive(ctx.channel().id());
        log.info("[{}] channel 被移除", ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Packet){
            Packet packet = (Packet) msg;
            System.out.println(packet.toString());
            if (packet.getType() == Packet.PACKET_REQUEST){
                onRequest(ctx, packet);
            }else{
                onResponse(ctx, packet);
            }
        }
    }

    private void onResponse(ChannelHandlerContext ctx, Packet packet) {

    }

    private void onRequest(ChannelHandlerContext ctx, Packet packet) {
        Packet response = executor.execute(ctx, packet);
        writeResponse(ctx, response);
    }

    private void writeResponse(ChannelHandlerContext ctx, Packet response) {
        if (ObjectUtil.isNotNull(response)){
            ctx.channel().writeAndFlush(response);
        }
    }

}
