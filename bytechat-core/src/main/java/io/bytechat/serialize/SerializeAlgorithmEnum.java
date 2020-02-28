package io.bytechat.serialize;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author : denglinhai
 * @date : 15:08 2020/2/28
 */
@Getter
public enum SerializeAlgorithmEnum {

    /**
     * fastjson
     */
    FAST_JSON((byte) 1);

    private byte algorithm;

    SerializeAlgorithmEnum(byte algorithm){
        this.algorithm = algorithm;
    }

    public static SerializeAlgorithmEnum getEnum(byte algorithm){
        return Arrays.stream(values()).filter(e -> e.getAlgorithm() == algorithm)
                                      .findFirst().orElse(null);
    }
}
