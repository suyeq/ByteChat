package io.bytechat.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.ClassScaner;
import cn.hutool.core.lang.Singleton;
import io.bytechat.init.InitAble;
import io.bytechat.init.InitOrder;
import io.bytechat.init.Initializer;
import io.bytechat.lang.config.BaseConfig;
import io.bytechat.lang.config.ConfigFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author : denglinhai
 * @date : 14:48 2020/4/17
 */
@Slf4j
@InitOrder(3)
public class InterceptorContext implements InitAble {

    private static AtomicBoolean init = new AtomicBoolean(false);

    private static List<AbstractInterceptor> abstractInterceptors = new ArrayList<>(16);

    private InterceptorContext(){

    }

    public static InterceptorContext getInstance(){
        return Singleton.get(InterceptorContext.class );
    }

    @Override
    public void init() {
        log.info("start init interceptor");
        doInitInterceptor();
    }

    public static List<AbstractInterceptor> getInterceptors(){
        if (!init.get()){
            log.info( "The interceptor initializer is not initialized");
            return new ArrayList<>();
        }
        return abstractInterceptors;
    }

    private void doInitInterceptor() {
        if (!init.compareAndSet(false, true)){
            log.info( "interceptor only init once");
            return;
        }
        try {
            BaseConfig baseConfig = ConfigFactory.getConfig(BaseConfig.class);
            Set<Class<?>> classSet = ClassScaner.scanPackageBySuper(baseConfig.basePackage(), AbstractInterceptor.class);
            if (CollectionUtil.isEmpty(classSet)) {
                log.info("no interceptor was initialized");
                return;
            }
            List<OrderWrapper> initList = new ArrayList<>();
            for (Class<?> clazz : classSet) {
                if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers()) || !AbstractInterceptor.class.isAssignableFrom(clazz)) {
                    continue;
                }
                try {
                    Constructor<?> constructor = clazz.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    AbstractInterceptor interceptor = (AbstractInterceptor) constructor.newInstance();
                    Interceptor intercept = interceptor.getClass().getAnnotation(Interceptor.class);
                    log.info("init interceptor ={}", intercept.name());
                    insertSorted(initList, interceptor);
                } catch (Exception e) {
                    log.warn("[InterceptorContext] init interceptor fail={}", e.getCause().toString());
                }
            }
            abstractInterceptors = initList.stream().map(e -> {
                return e.interceptor;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("[InterceptorContext] init interceptor fail={}", e.getCause().toString());
        } catch (Error error) {
            log.warn("[InterceptorContext] init interceptor error={}", error.getCause().toString());
            throw error;
        }
    }

    private static void insertSorted(List<OrderWrapper> list, AbstractInterceptor interceptor) {
        int order = resolveOrder(interceptor);
        int idx = 0;
        for (; idx < list.size(); idx++) {
            //排序加载
            if (list.get(idx).getOrder() > order) {
                break;
            }
        }
        list.add(idx, new OrderWrapper(order, interceptor));
    }

    private static int resolveOrder(AbstractInterceptor interceptor) {
        if (!interceptor.getClass().isAnnotationPresent(InitOrder.class)) {
            return InitOrder.LOWEST_PRIORITY;
        } else {
            return interceptor.getClass().getAnnotation(InitOrder.class).value();
        }
    }

    @Getter
    private static class OrderWrapper {
        private final int order;
        private final AbstractInterceptor interceptor;

        OrderWrapper(int order, AbstractInterceptor interceptor) {
            this.order = order;
            this.interceptor = interceptor;
        }
    }
}
