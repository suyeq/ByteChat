package io.bytechat.tcp.processor;

import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.entity.Request;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author : denglinhai
 * @date : 19:24 2020/3/2
 */
@Slf4j
public abstract class AbstractRequestProcessor implements RequestProcessor {

    @Override
    public Payload process(ChannelHandlerContext channelHandlerContext, Request request
//            , Back back
    ) {
        Payload payload = new Payload();
        try{
            payload = doProcessor(channelHandlerContext, request.getParams(), request.getPacketId());
        }catch (Exception e){
            //back.failure(e.getCause().toString());
            payload.setErrorMsg(0, e.getCause().toString());
            log.error("处理请求出错，出错原因：{}", e.getCause().toString());
        }
        //back.success(payload);
        return payload;
    }

    /**
     * process request
     * @param channelHandlerContext
     * @param params
     * @return
     */
    public abstract Payload doProcessor(ChannelHandlerContext channelHandlerContext, Map<String, Object> params, Long packetId) throws Exception;
}
