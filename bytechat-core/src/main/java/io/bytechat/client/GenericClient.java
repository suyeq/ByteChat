package io.bytechat.client;

import cn.hutool.core.lang.Assert;
import com.sun.corba.se.internal.CosNaming.BootstrapServer;
import io.bytechat.init.Initializer;
import io.bytechat.server.ServerAttr;
import io.bytechat.tcp.entity.Packet;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : denglinhai
 * @date : 13:58 2020/3/17
 */
@Slf4j
public class GenericClient implements Client {

    private ServerAttr serverAttr;

    public GenericClient(ServerAttr serverAttr){
        Assert.notNull(serverAttr, "serverAttr不能为空");
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
                .handler(new LoggingHandler())
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new ClientHandle());
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect(serverAttr.getAddress(), serverAttr.getPort());
        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                Channel channel = channelFuture.channel();
                if (future.isSuccess()){
                    log.info("[{}] 已经连上服务器{}", this.getClass().getSimpleName(), serverAttr);
                }else {
                    log.info("[{}]连接失败,原因{}", this.getClass().getSimpleName(), future.cause());
                }
            }
        });
    }

    @Override
    public void sendRequest(Packet packet) {

    }
}
