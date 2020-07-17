package io.bytechat.server;


import io.bytechat.server.channel.ChannelListener;
import io.bytechat.server.router.RouterService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : denglinhai
 * @date : 15:58 2020/5/9
 */
@Slf4j
public class ClusterServer extends AbstractServer {

    private RouterService routerService;

    public ClusterServer(Integer serverPort) {
        super(serverPort, ServerMode.CLUSTER);
        routerService = RouterService.getInstance();
    }

    public ClusterServer(Integer serverPort, ChannelListener channelListener){
        super(serverPort, channelListener, ServerMode.CLUSTER);
        routerService = RouterService.getInstance();
    }

    @Override
    public void registerRouter(ServerAttr serverAttr) {
        routerService.registerRouter(serverAttr.routerKey());
        log.info("server:{} register router success", serverAttr.toString());
    }
}
