package io.bytechat.service;

/**
 * @author : denglinhai
 * @date : 13:30 2020/4/7
 */
public interface UserService {

    /**
     * 注册用户并返回用户id
     * @param userName
     * @param password
     * @return
     */
    long register(String userName, String password);

    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    boolean login(String userName, String password);

    /**
     * 判断用户是否在线
     * @param userId
     * @return
     */
    boolean isOnline(Long userId);
}
