package io.bytechat;

import io.bytechat.server.StandaloneServer;
import io.bytechat.server.channel.DefaultChannelListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 * @author 溯夜
 */
//@SpringBootApplication
public class ByteChatServer {

    public static void main( String[] args )
    {
       // SpringApplication.run(ByteChatServer.class, args);
        new StandaloneServer(8899, DefaultChannelListener.newInstance()).start();
    }
}
