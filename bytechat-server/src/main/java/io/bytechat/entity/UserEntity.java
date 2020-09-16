package io.bytechat.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author : denglinhai
 * @date : 13:29 2020/4/7
 */
@Data
@Builder
@Accessors(chain = true)
//@TableName("user")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    //@TableId("id")
    private Long id;

    private String userName;

    private String password;

    private Long lastUpdateTime;

    private Integer isDelete;

    private Integer status;

    private String head;

    private String signature;
}
