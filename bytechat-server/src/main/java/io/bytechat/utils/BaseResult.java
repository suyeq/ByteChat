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

    private BaseResult() {

    }

    public void setErrorMessage(int code, String message) {
        this.success = false;
        this.code = code;
        this.msg = message;
    }

    public static BaseResult newSuccessResult(String msg){
        BaseResult result = new BaseResult();
        result.setMsg(msg);
        result.setCode(200);
        result.setSuccess(true);
        return result;
    }

    public static BaseResult newSuccessResult(String msg, Object content){
        BaseResult result = new BaseResult();
        result.setMsg(msg);
        result.setCode(200);
        result.setSuccess(true);
        result.setContent(content);
        return result;
    }

    public static BaseResult newErrorResult(Integer code, String msg){
        BaseResult result = new BaseResult();
        result.setMsg(msg);
        result.setCode(code);
        result.setSuccess(false);
        return result;
    }

}
