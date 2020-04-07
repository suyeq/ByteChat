package io.bytechat.service.impl;

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
    public long register(String userName, String password) {
        return 0;
    }

    @Override
    public boolean login(String userName, String password) {
        return false;
    }

    @Override
    public boolean isOnline(Long userId) {
        return false;
    }
}
