package io.bytechat.service.impl;

import io.bytechat.entity.MessageEntity;
import io.bytechat.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : denglinhai
 * @date : 13:33 2020/4/7
 */
@Slf4j
@Service
public class DefaultMessageService implements MessageService {

    @Override
    public void saveHistoryMsg(MessageEntity messageEntity) {

    }

    @Override
    public void saveOfflineMsg(MessageEntity messageEntity) {

    }
}
