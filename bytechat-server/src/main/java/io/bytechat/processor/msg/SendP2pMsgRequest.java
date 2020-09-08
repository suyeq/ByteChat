package io.bytechat.processor.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : denglinhai
 * @date : 15:07 2020/4/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendP2pMsgRequest{

    private Long toUserId;

    private String content;

    private Integer channelType;

    private Byte msgType;

    private Long sendTime;
}
