package io.bytechat.server.channel;

import cn.hutool.core.lang.Singleton;
import io.bytechat.server.ServerAttr;
import io.bytechat.server.ServerMode;
import io.bytechat.server.session.DefaultSessionManager;
import io.bytechat.server.session.SessionManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : denglinhai
 * @date : 16:33 2020/2/26
 */
@Slf4j
public class DefaultChannelListener implements ChannelListener {

    private ChannelManager channelManager;

    private SessionManager sessionManager;

    private ServerAttr serverAttr;

    private DefaultChannelListener(){
        this.channelManager = DefaultChannelManager.newInstance();
        this.sessionManager = DefaultSessionManager.newInstance();
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
    public void channelInactive(ChannelId channelId) {
        channelManager.removeChannel(channelId);
        sessionManager.removeSession(channelId);
        log.info("移除一个channelId={},", channelId);
    }

    @Override
    public void bindServerAttr(ServerAttr serverAttr) {
        this.serverAttr = serverAttr;
        sessionManager.bindServerAttr(serverAttr);
    }
}
