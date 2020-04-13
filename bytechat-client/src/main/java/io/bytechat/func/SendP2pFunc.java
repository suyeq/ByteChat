package io.bytechat.func;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import io.bytechat.lang.id.IdFactory;
import io.bytechat.lang.id.MemoryIdFactory;
import io.bytechat.lang.id.SnowflakeIdFactory;
import io.bytechat.server.channel.ChannelType;
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
 * @date : 15:27 2020/4/4
 */
@Slf4j
public class SendP2pFunc {

    private BaseFunc baseFunc;

    private IdFactory idFactory;

    public SendP2pFunc(BaseFunc baseFunc){
        Assert.notNull(baseFunc, "baseFunc不能为空");
        this.baseFunc = baseFunc;
        this.idFactory = SnowflakeIdFactory.getInstance();
    }

    public Payload sendP2pMsg(Long toUserId, Integer channelType, String msg, Byte msgType){
        Map<String, Object> params = buildParams(toUserId, channelType, msg, msgType);
        Request request = RequestFactory.newRequest(ImService.P2P_MSG, null, params);
        Packet packet = PacketFactory.newRequestPacket(request, idFactory.nextId());
        return baseFunc.sendRequest(packet);
    }

    private Map<String, Object> buildParams(Long toUserId, Integer channelType, String msg, Byte msgType){
        Map<String, Object> params = new HashMap<>();
        params.put("toUserId", toUserId);
        params.put("channelType", channelType);
        params.put("msgType", msgType);
        params.put("content", msg);
        params.put("sendTime", System.currentTimeMillis());
        return params;
    }

}
