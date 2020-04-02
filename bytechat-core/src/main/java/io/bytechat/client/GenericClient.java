package io.bytechat.client;

import cn.hutool.core.lang.Assert;
import io.bytechat.init.Initializer;
import io.bytechat.server.ServerAttr;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PacketFactory;
import io.bytechat.tcp.factory.PayloadFactory;
import io.bytechat.tcp.factory.PendingPackets;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.CompleteFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * @author : denglinhai
 * @date : 13:58 2020/3/17
 */
@Slf4j
public class GenericClient implements Client {

    private Channel channel;

    private ServerAttr serverAttr;

    private volatile boolean connect;

    public GenericClient(ServerAttr serverAttr){
        Assert.notNull(serverAttr, "serverAttr不能为空");
        this.connect = false;
        this.serverAttr = serverAttr;
        Initializer.init();
    }

    @Override
    public void connect() {
        Assert.notNull(serverAttr, "serverAttr不能为空");
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new ClientInitializer(GenericClient.this));
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect(serverAttr.getAddress(), serverAttr.getPort());
        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                channel = channelFuture.channel();
                if (future.isSuccess()){
                    connect = true;
                    log.info("[{}]已经连上服务器{}", this.getClass().getSimpleName(), serverAttr);
                }else {
                    log.info("[{}]连接失败,原因{}", this.getClass().getSimpleName(), future.cause());
                }
            }
        });
    }

    @Override
    public CompletableFuture<Packet> sendRequest(Packet request) {
        CompletableFuture<Packet> promise = new CompletableFuture<Packet>();
        if (!connect){
            String msg = "客户端尚未连接服务器";
            log.error(msg);
            Payload response = PayloadFactory.newErrorPayload(400, msg);
            promise.complete(PacketFactory.newResponsePacket(response, request.getId()));
            return promise;
        }
        PendingPackets.add(request.getId(), promise);
        ChannelFuture channelFuture = channel.writeAndFlush(request);
        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()){
                    log.info("[{}]消息发送完毕", request.getId());
                }else {
                    CompletableFuture<Packet> promise = PendingPackets.remove(request.getId());
                    if (promise != null){
                        promise.completeExceptionally(future.cause());
                    }
                }
            }
        });
        return promise;
    }
}
