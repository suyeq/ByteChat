package io.bytechat.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author : denglinhai
 * @date : 13:29 2020/4/7
 */
@Data
@Builder
@TableName("user")
public class UserEntity {

    @TableId("id")
    private Long userId;

    private String userName;

    private String password;

    private Long lastUpdateTime;

    private Integer isDelete;

}
