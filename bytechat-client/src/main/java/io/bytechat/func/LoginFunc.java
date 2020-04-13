package io.bytechat.func;

import cn.hutool.core.lang.Assert;
import io.bytechat.lang.id.IdFactory;
import io.bytechat.lang.id.SnowflakeIdFactory;
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
 * @date : 21:43 2020/3/31
 */
@Slf4j
public class LoginFunc {

    private BaseFunc baseFunc;

    private IdFactory idFactory;

    public LoginFunc(BaseFunc baseFunc){
        Assert.notNull(baseFunc, "传入的BaseFunc不能为空");
        this.baseFunc = baseFunc;
        this.idFactory = SnowflakeIdFactory.getInstance();
    }

    public Payload login(String userName, String password){
        Map<String, Object> params = buildParams(userName, password);
        Request request = RequestFactory.newRequest(ImService.LOGIN, null, params);
        Packet packet = PacketFactory.newRequestPacket(request, idFactory.nextId());
        return baseFunc.sendRequest(packet);
    }

    /**
     * 构建参数
     * @param userName
     * @param password
     * @return
     */
    private Map<String, Object> buildParams(String userName, String password){
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        params.put("password", password);
        return params;
    }
}
