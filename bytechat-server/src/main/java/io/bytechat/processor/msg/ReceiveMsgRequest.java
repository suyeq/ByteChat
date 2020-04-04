package io.bytechat.processor.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : denglinhai
 * @date : 14:52 2020/4/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveMsgRequest {

    private Long userId;

    private String userName;

    private Byte msgType;

    private String content;
}
