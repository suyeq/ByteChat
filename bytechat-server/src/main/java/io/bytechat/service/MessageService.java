package io.bytechat.service;

import io.bytechat.entity.MessageEntity;

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
}
