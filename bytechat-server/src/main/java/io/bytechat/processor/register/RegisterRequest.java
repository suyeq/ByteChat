package io.bytechat.processor.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : denglinhai
 * @date : 14:52 2020/4/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String userName;

    private String password;
}
