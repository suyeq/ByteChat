package io.bytechat.processor.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : denglinhai
 * @date : 16:26 2020/4/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOnlineUserRequest {

    private Long userId;
}
