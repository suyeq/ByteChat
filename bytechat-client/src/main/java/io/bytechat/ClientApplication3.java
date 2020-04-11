package io.bytechat;

import io.bytechat.client.Client;
import io.bytechat.client.SimpleClientFactory;
import io.bytechat.server.ServerAttr;


/**
 * Hello world!
 *
 * @author 溯夜
 */
public class ClientApplication3
{
    public static void main( String[] args ) throws InterruptedException {
        Client client= SimpleClientFactory.newInstance()
                .newClient(ServerAttr.getLocalServer(8899));
        client.connect();
        ClientBootstrap cli = new ClientBootstrap(client);
        cli.doCli();
    }
}
