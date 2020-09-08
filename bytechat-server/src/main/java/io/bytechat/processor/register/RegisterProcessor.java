package io.bytechat.processor.register;

import cn.hutool.core.bean.BeanUtil;
import io.bytechat.service.ImService;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.factory.PayloadFactory;
import io.bytechat.tcp.processor.AbstractRequestProcessor;
import io.bytechat.tcp.processor.Processor;
import io.bytechat.utils.BaseResult;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author : denglinhai
 * @date : 14:53 2020/4/6
 * 注册处理器
 */
@Slf4j
@Processor(name = ImService.REGISTER)
public class RegisterProcessor extends AbstractRequestProcessor {

    public RegisterProcessor(){

    }

    @Override
    public Payload doProcessor(ChannelHandlerContext channelHandlerContext, Map<String, Object> params, Long packetId) {
        return null;
    }
}
