package io.bytechat.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author : denglinhai
 * @date : 14:04 2020/4/7
 */
@Data
@TableName("groups")
public class GroupEntity {

    @TableId("id")
    private Long groupId;

    private String groupName;

    private Integer groupUsers;

    private Long createTime;

    private Long groupMasterId;
}
