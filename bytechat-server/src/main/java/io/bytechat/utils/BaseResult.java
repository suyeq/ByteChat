package io.bytechat.utils;

import lombok.Data;

/**
 * @author : denglinhai
 * @date : 20:26 2020/4/7
 */
@Data
public class BaseResult {
    
    private boolean success;
    
    private int code;
    
    private String msg;

    private Object content;

    public BaseResult() {
        this.success = true;
        this.code = 200;
        this.msg = "success";
    }

    public void setErrorMessage(int code, String message) {
        this.success = false;
        this.code = code;
        this.msg = message;
    }
}
