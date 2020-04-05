package io.bytechat.tcp.ctx;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.ClassScaner;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.StrUtil;
import io.bytechat.init.InitAble;
import io.bytechat.init.InitOrder;
import io.bytechat.lang.config.BaseConfig;
import io.bytechat.lang.config.ConfigFactory;
import io.bytechat.lang.util.MultipleUtil;
import io.bytechat.tcp.entity.Command;
import io.bytechat.tcp.processor.CommandProcessor;
import io.bytechat.tcp.processor.Processor;
import io.bytechat.tcp.processor.RequestProcessor;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : denglinhai
 * @date : 14:10 2020/4/4
 */
@Slf4j
@InitOrder(2)
public class CommandProcessorContext implements InitAble {

    private static AtomicBoolean init = new AtomicBoolean(false);

    private static Map<String, CommandProcessor> processorHolder = new ConcurrentHashMap<>();

    private CommandProcessorContext(){

    }

    public static CommandProcessorContext getInstance(){
        return Singleton.get(CommandProcessorContext.class);
    }

    @Override
    public void init() {
        log.info("开始初始化命令业务处理器");
        doInitCommandProcessor();
    }

    /**
     * 调用处理器处理请求
     * @param channelHandlerContext
     */
    public void process(ChannelHandlerContext channelHandlerContext, Command command){
        String requestName = MultipleUtil.unifiedProcessorName(command.getCommandName());
        CommandProcessor processor = processorHolder.get(requestName);
        System.out.println(processorHolder.toString());
        System.out.println(requestName);
        if (processor == null){
            log.info("请求处理器{}未发现", requestName);
            return;
        }
        processor.process(channelHandlerContext, command);
    }

    private void doInitCommandProcessor() {
        if (!init.compareAndSet(false, true)){
            log.info("命令处理器{}已被初始化", this.getClass().getSimpleName());
            return;
        }
        BaseConfig baseConfig = ConfigFactory.getConfig(BaseConfig.class);
        Set<Class<?>> classSet = ClassScaner.scanPackageBySuper(baseConfig.basePackage(), CommandProcessor.class);
        if (CollectionUtil.isEmpty(classSet)) {
            log.warn("未发现请求处理器");
            return;
        }
        for (Class<?> clazz : classSet) {
            if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers()) || !CommandProcessor.class.isAssignableFrom(clazz)) {
                continue;
            }
            try {
                // 检查处理器必须有processor注解
                Processor processor = clazz.getAnnotation(Processor.class);
                String serviceName = (processor != null && StrUtil.isNotBlank(processor.name())) ? MultipleUtil.unifiedProcessorName(processor.name()) : clazz.getName();
                cacheRequestProcessor(serviceName, clazz);
            } catch (Exception e) {
                log.warn("保存处理器失败", e.getMessage());
            }
        }
    }

    /**
     * 存贮处理器
     * @param serviceName
     * @param clazz
     */
    private void cacheRequestProcessor(String serviceName, Class<?> clazz) {
        if (processorHolder.containsKey(serviceName)){
            log.warn("不需要重复加载processor={}", serviceName);
            return;
        }
        log.info("加载processor={}成功", serviceName);
        processorHolder.putIfAbsent(serviceName, Singleton.get((Class<? extends  CommandProcessor>)clazz));
    }
}
