package io.bytechat.entity;


import lombok.Builder;
import lombok.Data;

/**
 * @author : denglinhai
 * @date : 14:04 2020/4/7
 */
@Data
@Builder
//@TableName("groups")
public class GroupEntity {

    //@TableId("id")
    private Long id;

    private String groupName;

    private Integer groupUsers;

    private Long createTime;

    private Long groupMasterId;
}
