package io.bytechat.processor.msg;

import cn.hutool.core.bean.BeanUtil;
import io.bytechat.service.ImService;
import io.bytechat.tcp.processor.AbstractCommandProcessor;
import io.bytechat.tcp.processor.Processor;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author : denglinhai
 * @date : 14:51 2020/4/4
 * 接收消息处理器
 */
@Slf4j
@Processor(name = ImService.RECV_MSG)
public class ReceiveMsgProcessor extends AbstractCommandProcessor {

    @Override
    public void doProcessor(ChannelHandlerContext ctx, Map<String, Object> params) {
        ReceiveMsgRequest request = BeanUtil.mapToBean(params, ReceiveMsgRequest.class,false);
        int isGroup = request.getIsGroup();
        if (isGroup == 1){
            String groupName = request.getGroupName();
            long groupId = request.getGroupId();
            String msg = request.getContent();
            System.out.println(String.format("groupMsg-%s(%d):\t%s", groupName, groupId, msg));
        }else {
            String msg = request.getContent();
            long userId = request.getUserId();
            String userName = request.getUserName();
            System.out.println(String.format("p2pMsg-%s(%d):\t%s", userName, userId, msg));
        }
        System.out.println("byteChat>");
    }
}
