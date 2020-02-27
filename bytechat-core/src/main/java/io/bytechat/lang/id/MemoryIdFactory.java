package io.bytechat.lang.id;

import cn.hutool.core.lang.Singleton;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : denglinhai
 * @date : 16:57 2020/2/27
 */
public class MemoryIdFactory implements IdFactory {

    private static AtomicInteger id;

    private MemoryIdFactory(){
        id = new AtomicInteger(0);
    }

    public static MemoryIdFactory newInstance(){
        return Singleton.get(MemoryIdFactory.class);
    }

    @Override
    public long nextId() {
        return id.incrementAndGet();
    }
}
