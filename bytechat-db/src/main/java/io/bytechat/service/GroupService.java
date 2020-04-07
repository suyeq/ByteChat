package io.bytechat.service;

import io.bytechat.entity.UserEntity;

import java.util.List;

/**
 * @author : denglinhai
 * @date : 14:18 2020/4/7
 */
public interface GroupService {

    /**
     * 从群组中获取全部在线用户
     * @param groupId
     * @return
     */
    List<UserEntity> fetchOnlineUserByGroupId(Long groupId);

    /**
     * 群组中获取全部用户
     * @param groupId
     * @return
     */
    List<UserEntity> fetchUsersByGroupId(Long groupId);
}
