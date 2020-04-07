package io.bytechat;

import io.bytechat.client.Client;
import io.bytechat.client.SimpleClientFactory;
import io.bytechat.func.BaseFunc;
import io.bytechat.func.LoginFunc;
import io.bytechat.server.ServerAttr;
import io.bytechat.tcp.entity.Payload;


/**
 * Hello world!
 *
 * @author 溯夜
 */
public class ClientApplication
{
    public static void main( String[] args ) throws InterruptedException {
        Client client= SimpleClientFactory.newInstance()
                .newClient(ServerAttr.getLocalServer(8899));
        client.connect();

        ClientBootstrap cli = new ClientBootstrap(client);
        cli.doCli();
    }
}
