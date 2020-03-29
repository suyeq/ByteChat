package io.bytechat.server.session;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSONObject;
import io.bytechat.lang.exception.ExceptionEnum;
import io.bytechat.lang.exception.SessionException;
import io.bytechat.server.channel.ChannelHelper;
import io.bytechat.server.channel.ChannelType;
import io.bytechat.server.channel.ChannelWrapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : denglinhai
 * @date : 22:25 2020/2/26
 */
@Slf4j
public class DefaultSession implements Session {

    private long userId;

    private Channel channel;

    private AtomicBoolean bind;

    private String sessionId;

    private ChannelType channelType;

    public DefaultSession(String sessionId){
        this.sessionId = sessionId;
        bind = new AtomicBoolean(false);
    }

    @Override
    public void bind(ChannelId channelId, long userId) {
        if (bind.compareAndSet(false, true)){
            ChannelWrapper channelWrapper = ChannelHelper.getChannelWrapper(channelId);
            Assert.notNull(channelWrapper, "channelWrapper不能为空");
            this.userId = userId;
            this.channel = channelWrapper.getChannel();
            this.channelType = channelWrapper.getChannelType();
        }
    }

    @Override
    public void writeAndFlush(Object msg) {
        if (!bind.get()){
            throw new IllegalStateException("session未绑定channel");
        }
        if (msg != null){
            channel.writeAndFlush(msg);
        }else {
            log.info("写入信息为空,userId={},channel={}", userId, channel);
        }
    }

    @Override
    public String sessionId() {
        if (!bind.get()){
            throw new SessionException(ExceptionEnum.SESSION_EXCEPTION_NOT_BIND);
        }
        return sessionId;
    }

    @Override
    public ChannelId channelId() {
        if (!bind.get()){
            throw new SessionException(ExceptionEnum.SESSION_EXCEPTION_NOT_BIND);
        }
        return channel.id();
    }

    @Override
    public ChannelType channelType() {
        if (!bind.get()){
            throw new SessionException(ExceptionEnum.SESSION_EXCEPTION_NOT_BIND);
        }
        return channelType;
    }

    @Override
    public long userId() {
        if (!bind.get()){
            throw new SessionException(ExceptionEnum.SESSION_EXCEPTION_NOT_BIND);
        }
        return userId;
    }

    @Override
    public String toString(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("channel", channel);
        jsonObject.put("sessionId", sessionId);
        jsonObject.put("userId", userId);
        return jsonObject.toJSONString();
    }
}
