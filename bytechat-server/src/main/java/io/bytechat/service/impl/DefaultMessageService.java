package io.bytechat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.bytechat.dao.MessageMapper;
import io.bytechat.dao.OffMessageMapper;
import io.bytechat.entity.MessageEntity;
import io.bytechat.entity.OffMessageEntity;
import io.bytechat.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : denglinhai
 * @date : 13:33 2020/4/7
 */
@Slf4j
@Service
public class DefaultMessageService implements MessageService {

    @Autowired
    private OffMessageMapper offMessageMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void saveHistoryMsg(MessageEntity messageEntity) {
        messageMapper.insert(messageEntity);
    }

    @Override
    public void saveOfflineMsg(MessageEntity messageEntity) {
        OffMessageEntity offMessageEntity = OffMessageEntity.builder().messageId(messageEntity.getMessageId())
                                            .groupId(messageEntity.getGroupId()).isGroup(messageEntity.getIsGroup())
                                            .recvUserId(messageEntity.getRecvUserId()).sendUserId(messageEntity.getSendUserId()).build();
        System.out.println(offMessageEntity.toString());
        offMessageMapper.insert(offMessageEntity);
    }

    @Override
    public List<MessageEntity> fetchOffP2pMsgByUserId(Long userId) {
        return messageMapper.fetchOffP2pMsgByUserId(userId);
    }

    @Override
    public MessageEntity fetchOffMsgByMsgId(Long msgId) {
        return null;
    }

    @Override
    public void deleteOffP2pMsgByUserId(Long userId) {
        QueryWrapper<OffMessageEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OffMessageEntity::getRecvUserId, userId);
        offMessageMapper.delete(queryWrapper);
    }
}
