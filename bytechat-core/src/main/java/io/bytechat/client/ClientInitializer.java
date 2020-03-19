package io.bytechat.client;

import io.bytechat.lang.config.BaseConfig;
import io.bytechat.lang.config.ConfigFactory;
import io.bytechat.tcp.codec.PacketCodec;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author : denglinhai
 * @date : 11:24 2020/3/18
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    private Client client;

    public ClientInitializer(Client client){
        this.client = client;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        BaseConfig config = ConfigFactory.getConfig(BaseConfig.class);
        pipeline.addLast(new HealthyChecker(config.pingInterval(), 3, client));
        pipeline.addLast(new PacketCodec());
        pipeline.addLast(new ClientHandle());
    }
}
