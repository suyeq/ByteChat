package io.bytechat.tcp.processor;

import io.bytechat.tcp.entity.Command;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author : denglinhai
 * @date : 14:23 2020/4/4
 */
@Slf4j
public abstract class AbstractCommandProcessor implements CommandProcessor{

    @Override
    public void process(ChannelHandlerContext ctx, Command command){
        try {
            doProcessor(ctx,command.getContent());
        }catch (Exception e){
            e.printStackTrace();
            log.info("执行命令处理器错误,原因={}", e.getCause().toString());
        }
    }

    /**
     * 执行处理器，延迟到子类实现
     * @param ctx
     * @param params
     */
    public abstract void doProcessor(ChannelHandlerContext ctx, Map<String, Object> params) throws Exception;
}
