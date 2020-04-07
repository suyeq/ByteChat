package io.bytechat.processor.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : denglinhai
 * @date : 20:31 2020/4/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMsgRequest {

    private Long groupId;

    private String content;

    private Byte msgType;
}
