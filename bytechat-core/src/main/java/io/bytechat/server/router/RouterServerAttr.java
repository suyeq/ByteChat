package io.bytechat.server.router;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : denglinhai
 * @date : 12:00 2020/5/9
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouterServerAttr {

    private Integer port;

    private String address;


}
