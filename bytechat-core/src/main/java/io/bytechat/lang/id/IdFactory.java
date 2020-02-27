package io.bytechat.lang.id;

/**
 * @author : denglinhai
 * @date : 16:47 2020/2/27
 * id工厂类
 */
public interface IdFactory {

    /**
     * 生成一个id
     * @return
     */
    long nextId();
}
