package io.bytechat.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author : denglinhai
 * @date : 14:07 2020/4/7
 */
@Data
@Builder
@TableName("group_user")
public class GroupUserEntity {

    private Long groupId;

    private Long userId;

    private Long lastAckMsgId;
}
