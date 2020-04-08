package io.bytechat.service.impl;

import io.bytechat.entity.UserEntity;
import io.bytechat.utils.BaseResult;
import io.bytechat.dao.UserMapper;
import io.bytechat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : denglinhai
 * @date : 13:32 2020/4/7
 */
@Slf4j
@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public BaseResult register(String userName, String password) {
        UserEntity user = UserEntity.builder().userName(userName)
                          .password(password).isDelete(0)
                          .lastUpdateTime(System.currentTimeMillis()).build();
        int id = userMapper.insert(user);
        BaseResult result = new BaseResult();
        result.setSuccess(true);
        return result;
    }

    @Override
    public BaseResult login(String userName, String password) {
        return null;
    }

    @Override
    public boolean isOnline(Long userId) {
        return false;
    }
}
