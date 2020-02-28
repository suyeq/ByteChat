package io.bytechat.serialize;

import cn.hutool.core.lang.Singleton;

/**
 * @author : denglinhai
 * @date : 15:07 2020/2/28
 */
public class DefaultSerializerChooser implements SerializerChooser {

    private DefaultSerializerChooser(){

    }

    public static SerializerChooser newInstance(){
        return Singleton.get(DefaultSerializerChooser.class);
    }

    @Override
    public Serializer choose(byte algorithm) {
        SerializeAlgorithmEnum algorithmEnum = SerializeAlgorithmEnum.getEnum(algorithm);
        if (algorithmEnum == null){
            return null;
        }
        switch (algorithmEnum){
            case  FAST_JSON:
                return FastJsonSerializer.newInstance();
            default:
                return null;
        }
    }
}
