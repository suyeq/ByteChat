package io.bytechat.func;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import io.bytechat.entity.UserEntity;
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
import java.util.List;
import java.util.Map;

/**
 * @author : denglinhai
 * @date : 20:18 2020/4/9
 */
@Slf4j
public class UserFunc {

    private BaseFunc baseFunc;

    private IdFactory idFactory;

    public UserFunc(BaseFunc baseFunc){
        Assert.notNull(baseFunc, "BaseFunc不能为空");
        this.baseFunc = baseFunc;
        this.idFactory = SnowflakeIdFactory.getInstance();
    }

    public Payload fetchOnlineUsers(){
        Request request = RequestFactory.newRequest(ImService.GET_ONLINE_USER, null, null);
        Packet packet = PacketFactory.newRequestPacket(request, idFactory.nextId());
        Payload payload = baseFunc.sendRequest(packet, false);
        //反序列化object对象，fastjson不会反序列化object
        List<UserEntity> userEntities = JSON.parseArray(payload.getResult().toString(), UserEntity.class);
        //List<UserEntity> userEntities = (List<UserEntity>) payload.getResult();
        payload.setResult(userEntities);
        return payload;
    }

    /**
     * 用户下线命令
     * @param usrId
     * @return
     */
    public Payload makeOfflineUser(Long usrId){
        Map<String, Object> params = buildParams(usrId);
        Request request = RequestFactory.newRequest(ImService.USER_OFFLINE, null, params);
        Packet packet = PacketFactory.newRequestPacket(request, idFactory.nextId());
        Payload payload = baseFunc.sendRequest(packet, false);
        baseFunc.closeConnection();
        return payload;
    }

    public Payload addFriend(Long userId){
        Map<String, Object> params = buildParams(userId);
        Request request = RequestFactory.newRequest(ImService.FRIEND_ADD, null, params);
        Packet packet = PacketFactory.newRequestPacket(request, idFactory.nextId());
        return baseFunc.sendRequest(packet, false);
    }

    private Map<String, Object> buildParams(Long userId){
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return params;
    }
}
