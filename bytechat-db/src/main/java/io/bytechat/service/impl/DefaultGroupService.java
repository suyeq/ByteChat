package io.bytechat.service.impl;

import io.bytechat.entity.UserEntity;
import io.bytechat.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : denglinhai
 * @date : 14:20 2020/4/7
 */
@Slf4j
@Service
public class DefaultGroupService implements GroupService {

    @Override
    public List<UserEntity> fetchOnlineUserByGroupId(Long groupId) {
        return null;
    }

    @Override
    public List<UserEntity> fetchUsersByGroupId(Long groupId) {
        return null;
    }
}
