package io.bytechat.serialize;

/**
 * @author : denglinhai
 * @date : 14:40 2020/2/28
 */
public interface Serializer {

    /**
     * 序列化字节
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
