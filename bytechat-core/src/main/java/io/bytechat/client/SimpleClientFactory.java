package io.bytechat.client;

import cn.hutool.core.lang.Singleton;
import io.bytechat.server.ServerAttr;

/**
 * @author : denglinhai
 * @date : 14:52 2020/3/17
 */
public class SimpleClientFactory implements ClientFactory{

    private SimpleClientFactory(){

    }

    public SimpleClientFactory newInstance(){
        return Singleton.get(SimpleClientFactory.class);
    }

    @Override
    public Client newClient(ServerAttr serverAttr) {
        return new GenericClient(serverAttr);
    }
}
