package io.bytechat.entity;

import lombok.Data;

/**
 * @author : denglinhai
 * @date : 13:58 2020/4/7
 */
@Data
public class MessageEntity {

    private Long messageId;

    private String content;

    private Byte msgType;

    private Long sendTime;

    private Long sendUserId;

    private Long recvUserId;

    private Integer isGroup;

    private Long groupId;
}
