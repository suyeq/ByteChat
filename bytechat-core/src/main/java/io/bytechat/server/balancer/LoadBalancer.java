package io.bytechat.server.balancer;

import io.bytechat.server.ServerAttr;

/**
 * @author : denglinhai
 * @date : 16:29 2020/5/9
 */
public interface LoadBalancer {

    /**
     * get a load balancer
     * @return
     */
    ServerAttr nextServer();
}
