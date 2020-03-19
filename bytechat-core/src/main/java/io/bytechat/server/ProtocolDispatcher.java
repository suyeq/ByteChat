package io.bytechat.server;

import io.bytechat.lang.config.BaseConfig;
import io.bytechat.lang.config.ConfigFactory;
import io.bytechat.lang.exception.ExceptionEnum;
import io.bytechat.lang.exception.ProtocolException;
import io.bytechat.server.channel.ChannelListener;
import io.bytechat.tcp.codec.PacketCodec;
import io.bytechat.tcp.entity.Packet;
import io.bytechat.tcp.handle.PacketHandle;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.Config;

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
        if (isTcp(magic1)){
            dispatcherToPacket(channelHandlerContext);
        }else {
            //未知的协议
            byteBuf.clear();
            channelHandlerContext.close();
            log.error("未知的协议channel={}", channelHandlerContext.channel());
        }
    }

    private void dispatcherToPacket(ChannelHandlerContext channelHandlerContext) {
        ChannelPipeline pipeline = channelHandlerContext.pipeline();
        BaseConfig config = ConfigFactory.getConfig(BaseConfig.class);
        pipeline.addLast(new IdleStateChecker(config.readIdlTime()));
        pipeline.addLast(new PacketCodec());
        pipeline.addLast(PacketHandle.newInstance(channelListener));
        //移除自身分发器，不然每次发送数据都会分发协议
        pipeline.remove(this);
        pipeline.fireChannelActive();
    }

    private boolean isTcp(int magic) {
        return magic == Packet.PACKET_MAGIC;
    }

}
