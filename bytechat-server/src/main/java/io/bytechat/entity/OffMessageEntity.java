package io.bytechat.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author : denglinhai
 * @date : 13:27 2020/4/12
 */
@Data
@Builder
@TableName("off_message")
public class OffMessageEntity {

    @TableId
    private Long messageId;

    private Long recvUserId;

    private Integer isGroup;

    private Long groupId;

    private Long sendUserId;

}
