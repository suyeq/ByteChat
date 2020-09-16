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
 * @date : 20:06 2020/5/8
 */
@Slf4j
public class GroupFunc {

    private BaseFunc baseFunc;

    private IdFactory idFactory;

    public GroupFunc(BaseFunc baseFunc){
        Assert.notNull(baseFunc, "BaseFunc不能为空");
        this.baseFunc = baseFunc;
        this.idFactory = SnowflakeIdFactory.getInstance();
    }

    public Payload createGroup(){
        Request request = RequestFactory.newRequest(ImService.GROUP_ADD, null, null);
        Packet packet = PacketFactory.newRequestPacket(request, idFactory.nextId());
        return baseFunc.sendRequest(packet, false);
    }

    public Payload joinGroup(Long groupId){
        Map<String, Object> params = buildParams(groupId);
        Request request = RequestFactory.newRequest(ImService.GROUP_JOIN, null, params);
        Packet packet = PacketFactory.newRequestPacket(request, idFactory.nextId());
        return baseFunc.sendRequest(packet, false);
    }

    private Map<String, Object> buildParams(Long groupId){
        Map<String, Object> params = new HashMap<>(4);
        params.put("groupId", groupId);
        return params;
    }
}
