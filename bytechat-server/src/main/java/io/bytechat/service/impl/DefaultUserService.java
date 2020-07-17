package io.bytechat.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.bytechat.entity.FriendEntity;
import io.bytechat.entity.UserEntity;
import io.bytechat.processor.login.LoginRequest;
import io.bytechat.server.session.Session;
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
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(password)){
            return BaseResult.newErrorResult(400, "用户名和密码不能为空");
        }
        UserEntity userEntity = UserEntity.builder().userName(userName)
                                .password(password).isDelete(0)
                                .lastUpdateTime(System.currentTimeMillis()).build();
        int rows = userMapper.insert(userEntity);
        return rows > 0 ? BaseResult.newSuccessResult("注册成功", userEntity) :
                BaseResult.newErrorResult(400,"该账号已注册");
    }

    @Override
    public BaseResult login(String userName, String password) {
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(password)){
            return BaseResult.newErrorResult(400, "用户名和密码不能为空");
        }
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getUserName, userName)
                .eq(UserEntity::getIsDelete, 0).eq(UserEntity::getPassword, password);
        UserEntity userEntity = userMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNull(userEntity)) {
            return BaseResult.newErrorResult(400, "用户密码错误");
        }
        return BaseResult.newSuccessResult("登陆成功", userEntity);
    }

    @Override
    public BaseResult addFriend(Long oneUserId, Long twoUserId) {
        if (ObjectUtil.isNull(oneUserId) || ObjectUtil.isNull(twoUserId)){
            return BaseResult.newErrorResult(400, "传入用户id不能为空");
        }
        FriendEntity friendEntity = buildFriendEntity(oneUserId, twoUserId);
        userMapper.addFriend(friendEntity);
        return BaseResult.newSuccessResult("添加好友成功");
    }

    @Override
    public boolean isFriend(Long userOneId, Long userTwoId) {
        if (ObjectUtil.isNull(userOneId) || ObjectUtil.isNull(userTwoId)){
            return false;
        }
        FriendEntity friendEntity = userMapper.fetchFriendByUserId(userOneId, userTwoId);
        return friendEntity == null ? false : true;
    }

    @Override
    public boolean isOnline(Long userId) {
        return false;
    }

    @Override
    public void update(LoginRequest loginRequest) {

    }

    private FriendEntity buildFriendEntity(Long oneUserId, Long twoUserId){
        return FriendEntity.builder().userOneId(oneUserId).userTwoId(twoUserId)
               .createTime(System.currentTimeMillis()).oneRemark("").twoRemark("").build();
    }

}
