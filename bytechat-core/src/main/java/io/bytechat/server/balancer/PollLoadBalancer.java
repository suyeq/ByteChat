package io.bytechat.server.balancer;

import cn.hutool.core.lang.Singleton;
import io.bytechat.server.ServerAttr;
import io.bytechat.server.router.RouterService;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : denglinhai
 * @date : 16:27 2020/5/9
 */
public class PollLoadBalancer implements LoadBalancer{

    private RouterService routerService;

    private AtomicInteger load = new AtomicInteger(0);

    private PollLoadBalancer(){
        routerService = RouterService.getInstance();
    }

    public static PollLoadBalancer getInstance(){
        return Singleton.get(PollLoadBalancer.class);
    }

    @Override
    public ServerAttr nextServer() {
        List<String> servers = routerService.getAllServers();
        int serverNum = servers.size();
        int oldIndex = load.get();
        int index = oldIndex % serverNum;
        load.compareAndSet(oldIndex, oldIndex + 1);
        String result = servers.get(index);
        String[] args = result.split(":");
        return ServerAttr.builder().address(args[0]).port(Integer.parseInt(args[1])).build();
    }
}
