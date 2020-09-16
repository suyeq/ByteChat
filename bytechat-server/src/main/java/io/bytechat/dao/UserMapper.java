package io.bytechat.dao;


import io.bytechat.entity.FriendEntity;


/**
 * @author : denglinhai
 * @date : 13:27 2020/4/7
 */
public interface UserMapper
        //extends BaseMapper<UserEntity>
{

    /**
     * 添加好友
     */
    void addFriend(FriendEntity friend);

    /**
     * 获取friend
     * @param userOneId
     * @param userTwoId
     * @return
     */
    FriendEntity fetchFriendByUserId(Long userOneId,  Long userTwoId);
}
