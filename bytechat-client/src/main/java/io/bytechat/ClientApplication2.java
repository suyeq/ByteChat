package io.bytechat;

import io.bytechat.client.Client;
import io.bytechat.client.SimpleClientFactory;
import io.bytechat.server.ServerAttr;


/**
 * Hello world!
 *
 * @author 溯夜
 */
public class ClientApplication2
{
    public static void main( String[] args ) throws InterruptedException {
        Client client= SimpleClientFactory.newInstance()
                .newClient(ServerAttr.getLocalServer(8899));
        client.connect();
        Thread.sleep(2000);
        ClientCommand cli = new ClientCommand(client);
        cli.doCli();
    }
}
