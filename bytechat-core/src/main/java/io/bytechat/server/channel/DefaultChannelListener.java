package io.bytechat.server.channel;

import cn.hutool.core.lang.Singleton;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : denglinhai
 * @date : 16:33 2020/2/26
 */
@Slf4j
public class DefaultChannelListener implements ChannelListener {

    private ChannelManager channelManager;

    private DefaultChannelListener(){
        this.channelManager = DefaultChannelManager.newInstance();
    }

    /**
     * 单例获取
     * @return
     */
    public static DefaultChannelListener newInstance(){
        return Singleton.get(DefaultChannelListener.class);
    }

    @Override
    public void channelActive(Channel channel, ChannelType channelType) {
        channelManager.addChannel(channel, channelType);
        log.info("增加一个新的channel={},channelType={}", channel, channelType);
    }

    @Override
    public void channelInactive(Channel channel) {
        channelManager.removeChannel(channel);
        log.info("移除一个channel={},", channel);
    }
}
