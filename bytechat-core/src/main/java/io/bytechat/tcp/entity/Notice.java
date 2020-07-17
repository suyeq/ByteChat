package io.bytechat.tcp.entity;

import lombok.Data;

import java.util.Map;

/**
 * @author : denglinhai
 * @date : 15:49 2020/7/1
 * 通知实体
 */
@Data
public class Notice {

    /**
     * mark is only notice,no content
     */
    private boolean isAck;

    /**
     *  content
     */
    private Map<String, Object> content;

}
