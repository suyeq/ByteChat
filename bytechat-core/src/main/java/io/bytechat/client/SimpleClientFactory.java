package io.bytechat.client;

import cn.hutool.core.lang.Singleton;
import io.bytechat.server.ServerAttr;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Request;
import io.bytechat.tcp.factory.PacketFactory;
import io.bytechat.tcp.factory.RequestFactory;

/**
 * @author : denglinhai
 * @date : 14:52 2020/3/17
 */
public class SimpleClientFactory implements ClientFactory{

    private SimpleClientFactory(){

    }

    public static SimpleClientFactory newInstance(){
        return Singleton.get(SimpleClientFactory.class);
    }

    @Override
    public Client newClient(ServerAttr serverAttr) {
        return new GenericClient(serverAttr);
    }

}
