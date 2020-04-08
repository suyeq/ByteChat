package io.bytechat;

import io.bytechat.server.StandaloneServer;
import io.bytechat.server.channel.DefaultChannelListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


/**
 * Hello world!
 *
 * @author 溯夜
 */
@SpringBootApplication(scanBasePackages = "io.bytechat")
@MapperScan("io.bytechat.dao")
public class ByteChatServer {

    public static void main( String[] args )
    {
        SpringApplication.run(ByteChatServer.class, args);
        new StandaloneServer(8899, DefaultChannelListener.newInstance()).start();
    }
}
