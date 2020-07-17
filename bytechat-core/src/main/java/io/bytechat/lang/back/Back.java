package io.bytechat.lang.back;

/**
 * @author : denglinhai
 * @date : 15:22 2020/7/2
 */
public interface Back {

    /**
     * success back
     * @param obj
     */
    void success(Object obj);

    /**
     * failure back
     * @param msg
     */
    void failure(String msg);
}
