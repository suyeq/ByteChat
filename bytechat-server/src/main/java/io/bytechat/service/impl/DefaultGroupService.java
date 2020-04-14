package io.bytechat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.bytechat.dao.GroupMapper;
import io.bytechat.dao.GroupUserMapper;
import io.bytechat.entity.GroupEntity;
import io.bytechat.entity.GroupUserEntity;
import io.bytechat.entity.UserEntity;
import io.bytechat.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private GroupUserMapper groupUserMapper;

    @Override
    public List<UserEntity> fetchOnlineUserByGroupId(Long groupId) {
        return null;
    }

    @Override
    public List<UserEntity> fetchUsersByGroupId(Long groupId) {
        return groupMapper.fetchUsersByGroupId(groupId);
    }

    @Override
    public void updateGroupMsgAckId(Long userId, Long msgId, Long groupId) {
        UpdateWrapper<GroupUserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(GroupUserEntity::getUserId, userId).eq(GroupUserEntity::getGroupId
                , groupId).set(GroupUserEntity::getLastAckMsgId, msgId);
        groupUserMapper.update(null, updateWrapper);
    }

    @Override
    public GroupEntity fetchGroupByGroupId(Long groupId) {
        return groupMapper.selectById(groupId);
    }

    @Override
    public List<GroupEntity> fetchGroupsByUserId(Long userId) {
        return groupMapper.fetchGroupsByUserId(userId);
    }
}
