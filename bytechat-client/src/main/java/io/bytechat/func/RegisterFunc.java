package io.bytechat.func;

import cn.hutool.core.lang.Assert;
import io.bytechat.lang.id.IdFactory;
import io.bytechat.lang.id.MemoryIdFactory;
import io.bytechat.service.ImService;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.entity.Request;
import io.bytechat.tcp.factory.PacketFactory;
import io.bytechat.tcp.factory.RequestFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : denglinhai
 * @date : 21:40 2020/4/8
 */
@Slf4j
public class RegisterFunc {

    private BaseFunc baseFunc;

    private IdFactory idFactory;

    public RegisterFunc(BaseFunc baseFunc){
        Assert.notNull(baseFunc, "BaseFunc不能为空");
        this.baseFunc = baseFunc;
        this.idFactory = MemoryIdFactory.newInstance();
    }

    public Payload register(String userName, String password){
        Map<String, Object> params = buildsParams(userName, password);
        Request request = RequestFactory.newRequest(ImService.REGISTER, null, params);
        Packet packet = PacketFactory.newRequestPacket(request, idFactory.nextId());
        return baseFunc.sendRequest(packet);
    }

    private Map<String, Object> buildsParams(String userName, String password){
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        params.put("password", params);
        return params;
    }
}
