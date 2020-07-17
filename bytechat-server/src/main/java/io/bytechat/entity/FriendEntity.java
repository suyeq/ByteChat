package io.bytechat.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author : denglinhai
 * @date : 17:07 2020/5/8
 */
@Data
@Builder
public class FriendEntity {

    private Long userOneId;

    private Long userTwoId;

    private Long createTime;

    private String oneRemark;

    private String twoRemark;
}
