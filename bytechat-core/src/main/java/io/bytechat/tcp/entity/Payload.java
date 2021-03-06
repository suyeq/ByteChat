package io.bytechat.tcp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : denglinhai
 * @date : 16:50 2020/2/28
 * response实体
 */
@Data
@NoArgsConstructor
public class Payload {

    private boolean success;

    private int code;

    private String msg;

    private Object result;

    public void setSuccessMsg(int code, String msg){
        this.success = true;
        this.code = code;
        this.msg = msg;
    }

    public void setErrorMsg(int code, String msg){
        this.success = false;
        this.code = code;
        this.msg = msg;
    }

}
