package io.bytechat.session;

import com.alibaba.fastjson.JSONObject;
import io.bytechat.server.session.DefaultSession;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : denglinhai
 * @date : 17:09 2020/3/29
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ImSession extends DefaultSession {

    private String userName;

    private String serverAddress;

    private Integer serverPort;

    public ImSession(String sessionId) {
        super(sessionId);
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        object.put("sessionId", sessionId());
        object.put("userId", userId());
        object.put("shortId", channelId().asShortText());
        object.put("longId", channelId().asLongText());
        object.put("channelType", channelType());
        object.put("userName", userName);
        object.put("serverAddress", serverAddress);
        object.put("serverPort", serverPort);
        return object.toJSONString();
    }
}
