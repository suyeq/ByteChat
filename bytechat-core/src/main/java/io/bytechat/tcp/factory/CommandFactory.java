package io.bytechat.tcp.factory;

import io.bytechat.tcp.entity.Command;

import java.util.Map;

/**
 * @author : denglinhai
 * @date : 15:44 2020/2/29
 */
public class CommandFactory {

    public static Command newCommand(String commandName, Map<String, Object> content){
        Command command = new Command();
        command.setCommandName(commandName);
        command.setContent(content);
        return command;
    }
}
