package io.bytechat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.bytechat.entity.FriendEntity;
import io.bytechat.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

/**
 * @author : denglinhai
 * @date : 13:27 2020/4/7
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 添加好友
     */
    void addFriend(@Param("friend")FriendEntity friend);

    /**
     * 获取friend
     * @param userOneId
     * @param userTwoId
     * @return
     */
    FriendEntity fetchFriendByUserId(@Param("userOneId") Long userOneId, @Param("userTwoId") Long userTwoId);
}
