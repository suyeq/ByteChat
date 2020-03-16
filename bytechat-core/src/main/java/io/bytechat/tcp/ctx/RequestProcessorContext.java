package io.bytechat.tcp.ctx;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.ClassScaner;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.StrUtil;
import io.bytechat.init.InitAble;
import io.bytechat.init.InitOrder;
import io.bytechat.lang.config.BaseConfig;
import io.bytechat.lang.config.ConfigFactory;
import io.bytechat.lang.util.MultipleUtil;
import io.bytechat.tcp.entity.Payload;
import io.bytechat.tcp.entity.Request;
import io.bytechat.tcp.factory.PayloadFactory;
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
 * @date : 19:43 2020/3/2
 * 请求处理器上下文
 */
@Slf4j
@InitOrder(1)
public class RequestProcessorContext implements InitAble {

    private static AtomicBoolean init = new AtomicBoolean(false);

    private static Map<String, RequestProcessor> processorHolder = new ConcurrentHashMap<>();

    private RequestProcessorContext(){

    }

    @Override
    public void init() {
        doInitRequestProcessor();
    }

    /**
     * 调用处理器处理请求
     * @param channelHandlerContext
     * @return
     */
    public Payload process(ChannelHandlerContext channelHandlerContext, Request request){
        String requestName = MultipleUtil.unifiedProcessorName(request.getServiceName());
        RequestProcessor processor = processorHolder.get(requestName);
        if (processor == null){
            return PayloadFactory.newErrorPayload(0, StrFormatter.format(" serviceName={}请求处理器未发现", requestName));
        }
        return processor.process(channelHandlerContext, request);
    }

    private void doInitRequestProcessor() {
        if (!init.compareAndSet(false, true)) {
            return;
        }
        BaseConfig baseConfig = ConfigFactory.getConfig(BaseConfig.class);
        Set<Class<?>> classSet = ClassScaner.scanPackageBySuper(baseConfig.basePackage(), RequestProcessor.class);
        if (CollectionUtil.isEmpty(classSet)) {
            log.warn("未发现请求处理器");
            return;
        }
        for (Class<?> clazz : classSet) {
            if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers()) || !RequestProcessor.class.isAssignableFrom(clazz)) {
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
        processorHolder.putIfAbsent(serviceName, Singleton.get((Class<? extends  RequestProcessor>)clazz));
    }
}
