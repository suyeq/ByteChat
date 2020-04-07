package io.bytechat.processor.msg;

import cn.hutool.core.bean.BeanUtil;
import io.bytechat.service.ImService;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.processor.AbstractRequestProcessor;
import io.bytechat.tcp.processor.Processor;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author : denglinhai
 * @date : 20:31 2020/4/6
 */
@Slf4j
@Processor(name = ImService.GROUP_MSG)
public class GroupMsgProcessor extends AbstractRequestProcessor {

    private SendP2pMsgProcessor sendP2pMsgProcessor;

    public GroupMsgProcessor(){
        this.sendP2pMsgProcessor = new SendP2pMsgProcessor();
    }

    @Override
    public Payload doProcessor(ChannelHandlerContext channelHandlerContext, Map<String, Object> params) {
        GroupMsgRequest request = BeanUtil.mapToBean(params, GroupMsgRequest.class, false);

        return null;
    }
}
