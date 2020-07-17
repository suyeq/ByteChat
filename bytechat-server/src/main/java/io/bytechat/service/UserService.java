package io.bytechat.service;

import io.bytechat.processor.login.LoginRequest;
import io.bytechat.server.session.Session;
import io.bytechat.utils.BaseResult;

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
    BaseResult register(String userName, String password);

    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    BaseResult login(String userName, String password);

    /**
     * 添加好友
     * @param oneUserId
     * @param twoUserId
     */
    BaseResult addFriend(Long oneUserId, Long twoUserId);

    /**
     * 判断是否是好友
     * @param userOneId
     * @param userTwoId
     * @return
     */
    boolean isFriend(Long userOneId, Long userTwoId);

    /**
     * 判断用户是否在线
     * @param userId
     * @return
     */
    boolean isOnline(Long userId);


    void update(LoginRequest loginRequest);
}
