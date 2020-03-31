package io.bytechat.server;

import cn.hutool.core.util.ObjectUtil;
import io.bytechat.init.Initializer;
import io.bytechat.lang.config.BaseConfig;
import io.bytechat.lang.config.ConfigFactory;
import io.bytechat.server.channel.ChannelListener;
import io.bytechat.server.channel.DefaultChannelListener;
import io.bytechat.server.session.AbstractSessionManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : denglinhai
 * @date : 13:34 2020/2/26
 */
@Slf4j
public abstract class AbstractServer implements Server{

    private ServerAttr serverAttr;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    private ChannelListener channelListener;

    /**
     * 只允许启动一次服务
     */
    private AtomicBoolean start = new AtomicBoolean(false);

    public AbstractServer(Integer serverPort){
        this(serverPort, null);
    }

    public AbstractServer(Integer serverPort, ChannelListener channelListener){
        int port = ObjectUtil.isNull(serverPort) ? ConfigFactory.getConfig(BaseConfig.class).serverPort() : serverPort;
        this.serverAttr = ServerAttr.getLocalServer(port);
        this.channelListener = channelListener == null ? DefaultChannelListener.newInstance() : channelListener;
    }

    @Override
    public void start(){
        if (start.compareAndSet(false, true)){
            Initializer.init();
            doStart(serverAttr);
        }
    }

    @Override
    public void stop(){
        if (!start.get()){
            log.warn("服务尚未启动");
            return;
        }
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }

    private void doStart(ServerAttr serverAttr){
        long start = System.currentTimeMillis();
        bossGroup = new NioEventLoopGroup();
        workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new ProtocolDispatcher(channelListener));
                    }
                });

        ChannelFuture future = bootstrap.bind(serverAttr.getPort());
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                long spend = System.currentTimeMillis() - start;
                if (channelFuture.isSuccess()){
                    log.info("{}服务启动成功，端口号[{}]，花费{}ms", this.getClass().getSimpleName(), serverAttr.getPort(), spend);
                }
            }
        });
    }
}
