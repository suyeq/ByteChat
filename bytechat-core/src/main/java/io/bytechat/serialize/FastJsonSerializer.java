package io.bytechat.serialize;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Singleton;
import com.alibaba.fastjson.JSON;

/**
 * @author : denglinhai
 * @date : 14:45 2020/2/28
 * fastjson序列化
 */
public class FastJsonSerializer implements Serializer {

    private FastJsonSerializer(){

    }

    public static FastJsonSerializer newInstance(){
        return Singleton.get(FastJsonSerializer.class);
    }

    @Override
    public byte[] serialize(Object object) {
        Assert.notNull(object, "参数不能为空");
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Assert.notNull(bytes, "数据不能为空");
        return JSON.parseObject(bytes, clazz);
    }
}
