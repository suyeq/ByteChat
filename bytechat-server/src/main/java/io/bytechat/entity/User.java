package io.bytechat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.jws.soap.SOAPBinding;

/**
 * @author : denglinhai
 * @date : 15:28 2020/3/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int channelType;

    private String sessionId;

    private long userId;

    private String userName;

    private String password;

}
