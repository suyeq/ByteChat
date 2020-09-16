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
 * @date : 13:21 2020/4/11
 */
@Slf4j
public class SendGroupMsgFunc{

    private BaseFunc baseFunc;

    private IdFactory idFactory;

    public SendGroupMsgFunc(BaseFunc baseFunc){
        Assert.notNull(baseFunc, "BaseFunc不能为空");
        this.baseFunc = baseFunc;
        this.idFactory = SnowflakeIdFactory.getInstance();
    }

    public Payload sendGroupMsg(Long groupId, String msg, Integer channelType, Byte msgType){
        Map<String, Object> params = buildParams(groupId, msg, channelType, msgType);
        Request request = RequestFactory.newRequest(ImService.GROUP_MSG, null, params);
        Packet packet = PacketFactory.newRequestPacket(request, idFactory.nextId());
        return baseFunc.sendRequest(packet, true);
    }

    private Map<String, Object> buildParams(Long groupId, String msg, Integer channelType, Byte msgType){
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("content", msg);
        params.put("channelType", channelType);
        params.put("msgType", msgType);
        params.put("sendTime", System.currentTimeMillis());
        return params;
    }


}
