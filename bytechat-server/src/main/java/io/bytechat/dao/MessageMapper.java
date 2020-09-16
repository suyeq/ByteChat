package io.bytechat.dao;

import io.bytechat.entity.MessageEntity;


import java.util.List;

/**
 * @author : denglinhai
 * @date : 13:28 2020/4/7
 */
public interface MessageMapper
       // extends BaseMapper<MessageEntity>
{

    /**
     * 获取离线消息根据userId
     * @param userId
     * @return
     */
    public List<MessageEntity> fetchOffP2pMsgByUserId(Long userId);

    /**
     * get group off_msg
     * @param userId
     * @param groupId
     * @return
     */
    public List<MessageEntity> fetchOffGroupMsgByUserIdAndGroupId(Long userId, Long groupId);
}
