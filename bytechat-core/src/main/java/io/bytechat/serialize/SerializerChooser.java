package io.bytechat.serialize;

/**
 * @author : denglinhai
 * @date : 15:03 2020/2/28
 * 序列化选择器
 */
public interface SerializerChooser {

    /**
     * 选择序列化方法
     * @param algorithm
     * @return
     */
    Serializer choose(byte algorithm);
}
