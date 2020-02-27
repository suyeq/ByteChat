package io.bytechat.server.channel;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Singleton;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author : denglinhai
 * @date : 16:31 2020/2/26
 */
public class DefaultChannelManager implements ChannelManager {

    private ChannelGroup channelGroup;

    private final static AttributeKey<ChannelType> CHANNEL_TYPE_ATTRIBUTE_KEY = AttributeKey.newInstance("channelType");

    private DefaultChannelManager(){
        this.channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    public static DefaultChannelManager newInstance(){
        return Singleton.get(DefaultChannelManager.class);
    }

    @Override
    public void addChannel(Channel channel, ChannelType channelType) {
        Assert.notNull(channel, "channel不能为空");
        Assert.notNull(channelType, "channelType不能为空");
        channel.attr(CHANNEL_TYPE_ATTRIBUTE_KEY).set(channelType);
        channelGroup.add(channel);
    }

    @Override
    public void removeChannel(ChannelId channelId) {
        Assert.notNull(channelId, "channelId不能为空");
        channelGroup.remove(channelId);
    }

    @Override
    public ChannelWrapper getChannelWrapper(ChannelId channelId) {
        Assert.notNull(channelId, "channelId不能为空");
        Channel channel = channelGroup.isEmpty() ? null : channelGroup.find(channelId);
        return channel == null ? null : new ChannelWrapper(channel, channel.attr(CHANNEL_TYPE_ATTRIBUTE_KEY).get());
    }
}
