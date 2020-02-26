package io.bytechat.server.channel;

import io.netty.channel.ChannelId;

/**
 * @author : denglinhai
 * @date : 22:37 2020/2/26
 */
public class ChannelHelper {

    private static ChannelManager channelManager = DefaultChannelManager.newInstance();

    private ChannelHelper(){
        //do nothing......
    }

    public static ChannelWrapper getChannelWrapper(ChannelId channelId){
        return channelManager.getChannelWrapper(channelId);
    }
}
