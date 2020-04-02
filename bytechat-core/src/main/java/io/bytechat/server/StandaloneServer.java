package io.bytechat.server;

import io.bytechat.server.channel.ChannelListener;
import io.bytechat.server.channel.DefaultChannelListener;

/**
 * @author : denglinhai
 * @date : 20:08 2020/3/16
 * 单例服务器
 */
public class StandaloneServer extends AbstractServer {

    public StandaloneServer(Integer serverPort) {
        super(serverPort);
    }

    public StandaloneServer(Integer serverPort, ChannelListener channelListener){
        super(serverPort, channelListener);
    }

}
