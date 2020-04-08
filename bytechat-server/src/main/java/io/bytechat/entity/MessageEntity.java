package io.bytechat.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author : denglinhai
 * @date : 13:58 2020/4/7
 */
@Data
@Builder
@TableName("message")
public class MessageEntity {

    @TableId("id")
    private Long messageId;

    private String content;

    private Byte msgType;

    private Long sendTime;

    private Long sendUserId;

    private Long recvUserId;

    private Integer isGroup;

    private Long groupId;
}
