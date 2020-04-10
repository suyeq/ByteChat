package io.bytechat.service.impl;

import io.bytechat.dao.GroupMapper;
import io.bytechat.entity.GroupEntity;
import io.bytechat.entity.UserEntity;
import io.bytechat.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : denglinhai
 * @date : 14:20 2020/4/7
 */
@Slf4j
@Service
public class DefaultGroupService implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public List<UserEntity> fetchOnlineUserByGroupId(Long groupId) {
        return null;
    }

    @Override
    public List<UserEntity> fetchUsersByGroupId(Long groupId) {
        return groupMapper.fetchUsersByGroupId(groupId);
    }

    @Override
    public void updateGroupMsgAckId(Long userId, Long msgId) {

    }

    @Override
    public GroupEntity fetchGroupByGroupId(Long groupId) {
        return groupMapper.selectById(groupId);
    }
}
