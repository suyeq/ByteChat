package io.bytechat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.bytechat.dao.GroupMapper;
import io.bytechat.dao.GroupUserMapper;
import io.bytechat.entity.GroupEntity;
import io.bytechat.entity.GroupUserEntity;
import io.bytechat.entity.UserEntity;
import io.bytechat.service.GroupService;
import io.bytechat.utils.BaseResult;
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

    @Override
    public BaseResult createGroup(Long masterUserId, String groupName) {
        GroupEntity groupEntity = GroupEntity.builder().groupMasterId(masterUserId)
                                             .groupName(groupName).groupUsers(0)
                                             .createTime(System.currentTimeMillis()).build();
        int rows = groupMapper.insert(groupEntity);
        if (rows > 0){
            return BaseResult.newSuccessResult("create group success", groupEntity.getId());
        }
        return BaseResult.newErrorResult(400, "create group fail cause: db execute error");
    }

    @Override
    public BaseResult joinGroup(Long userId, Long groupId) {
        GroupUserEntity groupUserEntity = GroupUserEntity.builder().userId(userId)
                                          .groupId(groupId).lastAckMsgId(0L).build();
        int rows = groupUserMapper.insert(groupUserEntity);
        if (rows > 0){
            return BaseResult.newSuccessResult("join group success");
        }
        return BaseResult.newErrorResult(400, "join group fail cause: db execute error");
    }

    @Override
    public boolean groupIsHaveUser(Long groupId, Long userId) {
        QueryWrapper<GroupUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GroupUserEntity::getGroupId, groupId)
                             .eq(GroupUserEntity::getUserId, userId);
        GroupUserEntity groupUserEntity = groupUserMapper.selectOne(queryWrapper);
        return groupUserEntity == null ? false : true;
    }
}
