package io.bytechat.tcp.entity;

import lombok.Data;

import java.util.Map;

/**
 * @author : denglinhai
 * @date : 16:55 2020/2/28
 * 命令实体
 */
@Data
public class Command {

    private String commandName;

    private Map<String, Object> content;
}
