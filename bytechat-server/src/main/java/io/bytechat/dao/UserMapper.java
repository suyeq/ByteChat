package io.bytechat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.bytechat.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : denglinhai
 * @date : 13:27 2020/4/7
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
