package io.bytechat.server;

import io.bytechat.lang.exception.ExceptionEnum;
import io.bytechat.lang.exception.ProtocolException;
import io.bytechat.server.channel.ChannelListener;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author : denglinhai
 * @date : 13:59 2020/2/28
 * 协议分发器
 */
@Slf4j
public class ProtocolDispatcher extends ByteToMessageDecoder {

    private ChannelListener channelListener;

    public ProtocolDispatcher(ChannelListener channelListener){
        this.channelListener = channelListener;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 5){
            log.error("协议格式错误,channel={}", channelHandlerContext.channel());
            return;
        }
        int readerIndex = byteBuf.readerIndex();
        int magic1 = byteBuf.getByte(readerIndex);
        int magic2 = byteBuf.getByte(readerIndex + 1);
        if (isTcp()){
            //
        }else {
            //未知的协议
            byteBuf.clear();
            channelHandlerContext.close();
            log.error("未知的协议channel={}", channelHandlerContext.channel());
        }
    }

    private boolean isTcp() {
        return false;
    }

}
