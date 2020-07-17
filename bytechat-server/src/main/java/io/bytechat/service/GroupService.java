package io.bytechat.service;

import io.bytechat.entity.GroupEntity;
import io.bytechat.entity.UserEntity;
import io.bytechat.utils.BaseResult;

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

    /**
     * 更新接收群消息的ack_msg_id
     * @param userId
     * @param msgId
     * @param groupId
     */
    void updateGroupMsgAckId(Long userId, Long msgId, Long groupId);

    /**
     * 获取一个群组信息
     * @param groupId
     * @return
     */
    GroupEntity fetchGroupByGroupId(Long groupId);

    /**
     * 获取用户群组
     * @param userId
     * @return
     */
    List<GroupEntity> fetchGroupsByUserId(Long userId);

    /**
     * 创建一个群组
     * @param masterUserId
     * @param groupName
     */
    BaseResult createGroup(Long masterUserId, String groupName);

    /**
     * 加入一个群组
     * @param userId
     * @param groupId
     * @return
     */
    BaseResult joinGroup(Long userId, Long groupId);

    /**
     * 判断群是否有该成员
     * @param groupId
     * @param userId
     * @return
     */
    boolean groupIsHaveUser(Long groupId, Long userId);
}
