package io.bytechat.entity;

import lombok.Data;

/**
 * @author : denglinhai
 * @date : 14:04 2020/4/7
 */
@Data
public class Group {

    private Long groupId;

    private String groupName;

    private Integer groupUsers;

    private Long createTime;

    private Long groupMaster;
}
