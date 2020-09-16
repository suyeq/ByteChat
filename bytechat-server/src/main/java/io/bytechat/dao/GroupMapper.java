package io.bytechat.dao;


import io.bytechat.entity.GroupEntity;
import io.bytechat.entity.UserEntity;

import java.util.List;

/**
 * @author : denglinhai
 * @date : 14:18 2020/4/7
 */
public interface GroupMapper
       // extends BaseMapper<GroupEntity>
{

    /**
     * 群组中获取全部用户
     * @param groupId
     * @return
     */
    List<UserEntity> fetchUsersByGroupId(Long groupId);

    /**
     * 获取用户群组
     * @param userId
     * @return
     */
    List<GroupEntity> fetchGroupsByUserId(Long userId);
}
