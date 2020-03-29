package io.bytechat.processor.login;

import com.sun.tracing.dtrace.ArgsAttributes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : denglinhai
 * @date : 15:46 2020/3/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    private String userName;

    private String password;
}
