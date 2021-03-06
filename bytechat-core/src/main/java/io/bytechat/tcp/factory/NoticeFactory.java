package io.bytechat.tcp.factory;

import io.bytechat.tcp.entity.Notice;

import java.util.Map;

/**
 * @author : denglinhai
 * @date : 16:32 2020/7/2
 */
public class NoticeFactory {

    public static Notice newNotice(){
        Notice notice = new Notice();
        notice.setAck(true);
        return notice;
    }

    public static Notice newNotice(Map<String, Object> content){
        Notice notice = new Notice();
        notice.setAck(false);
        notice.setContent(content);
        return notice;
    }
}
