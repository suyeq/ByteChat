package io.bytechat.server.router;

import cn.hutool.core.lang.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : denglinhai
 * @date : 12:04 2020/5/9
 */
@Slf4j
public class RouterService {


    private RouterService(){

    }

    public static RouterService getInstance(){
        return Singleton.get(RouterService.class);
    }

    /**
     * 服务注册路由
     */
    public void registerRouter(String ip){
        log.info("server online success");
    }

    /**
     * session绑定路由
     */
    public void bindRouter(String sessionId, String ip){
        log.info("session bind success, sessionId={}", sessionId);
    }

    /**
     * 解绑路由
     * @param sessionId
     */
    public void untieRouter(String sessionId, String ip){
        log.info("session untie success, sessionId={}", sessionId);
    }

    /**
     * server offline,cancel register
     */
    public void cancelRouter(String ip){
        log.info("server offline success");
    }

    /**
     * numbers of servers
     * @return
     */
    public int serverNumbers(){
        return 0;
    }

    /**
     * 获取全部在线server
     * @return
     */
    public List<String> getAllServers(){

        return null;
    }
}
