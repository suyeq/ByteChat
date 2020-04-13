package io.bytechat.service;

import io.bytechat.entity.MessageEntity;

import java.util.List;

/**
 * @author : denglinhai
 * @date : 13:30 2020/4/7
 */
public interface MessageService {

    /**
     * 保存历史消息
     * @param messageEntity
     */
    void saveHistoryMsg(MessageEntity messageEntity);

    /**
     * 保存离线消息
     * @param messageEntity
     */
    void saveOfflineMsg(MessageEntity messageEntity);

    /**
     * 获取离线消息根据userId
     * @param userId
     * @return
     */
    List<MessageEntity> fetchOffP2pMsgByUserId(Long userId);

    /**
     * 根据离线消息Id获取离线消息
     * @param msgId
     * @return
     */
    MessageEntity fetchOffMsgByMsgId(Long msgId);

    /**
     * 根据userId删除离线消息，p2p
     * @param userId
     */
    void deleteOffP2pMsgByUserId(Long userId);
}
