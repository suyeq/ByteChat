package io.bytechat.client;

import cn.hutool.core.lang.Assert;
import io.bytechat.confirm.MsgMonitorHandlerManager;
import io.bytechat.init.Initializer;
import io.bytechat.server.ServerAttr;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PacketFactory;
import io.bytechat.tcp.factory.PayloadFactory;
import io.bytechat.tcp.factory.PendingPackets;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
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

    private EventLoopGroup eventLoopGroup;

    private volatile boolean connect;

    private MsgMonitorHandlerManager monitorManager;

    public GenericClient(){
        this.connect = false;
        Initializer.init();
        monitorManager = MsgMonitorHandlerManager.getInstance();
    }

    public GenericClient(ServerAttr serverAttr){
        Assert.notNull(serverAttr, "serverAttr不能为空");
        this.connect = false;
        this.serverAttr = serverAttr;
        Initializer.init();
        monitorManager = MsgMonitorHandlerManager.getInstance();
    }

    @Override
    public boolean connect(){
        // 默认serverAttr不传就走集群路线
        if (serverAttr == null){
            //serverAttr = loadBalancer.nextServer();
        }
        Assert.notNull(serverAttr, "serverAttr不能为空");
        eventLoopGroup = new NioEventLoopGroup();
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

        ChannelFuture channelFuture = bootstrap.
                      connect(serverAttr.getAddress(), serverAttr.getPort());
        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                channel = channelFuture.channel();
                if (future.isSuccess()){
                    connect = true;
                    log.info("{}已经连上服务器{}", this.getClass().getSimpleName(), serverAttr);
                }else {
                    log.info("{}连接失败,原因{}", this.getClass().getSimpleName(), future.cause());
                }
            }
        });
        return connect == true ? true : false;
    }

    @Override
    public void closeConnect() {
        if (!connect){
            log.info("client not connect server");
            return;
        }
        connect = false;
        eventLoopGroup.shutdownGracefully();
        System.exit(-1);
    }

    @Override
    public void connectionStateReset() {
        connect = false;
    }

    @Override
    public boolean isClose() {
        return !connect;
    }

    @Override
    public void messageDelivery(Packet packet) {
        monitorManager.removeHandler(packet);
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
        //TODO: how make this msg in queue...
        //...
        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (!future.isSuccess()){
                    CompletableFuture<Packet> promise = PendingPackets.remove(request.getId());
                    promise.completeExceptionally(future.cause());
                }
                //monitorManager.removeHandler(request);
            }
        });
        return promise;
    }

    @Override
    public CompletableFuture<Packet> sendRequest(Packet request, boolean isJoinMsgMonitor) {
        if (isJoinMsgMonitor){
            //添加消息监听
            monitorManager.addHandler(request, this);
        }
        return sendRequest(request);
    }
}
