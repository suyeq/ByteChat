package io.bytechat.server.session;

import cn.hutool.core.lang.Assert;
import io.bytechat.server.channel.ChannelHelper;
import io.bytechat.server.channel.ChannelWrapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : denglinhai
 * @date : 22:25 2020/2/26
 */
public class DefaultSession implements Session {

    private long userId;

    private Channel channel;

    private AtomicBoolean bind;

    private String sessionId;

    public DefaultSession(String sessionId){
        this.sessionId = sessionId;
        bind = new AtomicBoolean(false);
    }

    @Override
    public void bind(ChannelId channelId, long userId) {
        if (bind.compareAndSet(false, true)){
            ChannelWrapper channelWrapper = ChannelHelper.getChannelWrapper(channelId);
            Assert.notNull(channelWrapper, "channelWrapper不能为空");
            this.userId = userId;
            this.channel = channelWrapper.getChannel();
        }
    }

    @Override
    public String sessionId() {
        if (!bind.get()){
            //throw
        }
        return sessionId;
    }

    @Override
    public ChannelId channelId() {
        return channel.id();
    }

    @Override
    public long userId() {
        return userId;
    }
}
