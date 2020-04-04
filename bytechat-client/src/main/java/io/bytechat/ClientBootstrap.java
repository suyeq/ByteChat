package io.bytechat;

import io.bytechat.client.Client;
import io.bytechat.func.BaseFunc;
import io.bytechat.func.LoginFunc;
import io.bytechat.func.SendP2pFunc;
import io.bytechat.tcp.entity.Payload;

import java.util.Scanner;

/**
 * @author : denglinhai
 * @date : 16:03 2020/4/4
 */
public class ClientBootstrap {

    private BaseFunc baseFunc;

    private LoginFunc loginFunc;

    private SendP2pFunc sendP2pFunc;

    public ClientBootstrap(Client client){
        this.baseFunc = new BaseFunc(client);
        this.loginFunc = new LoginFunc(baseFunc);
        this.sendP2pFunc = new SendP2pFunc(baseFunc);
    }


    public void doCli(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            String msg = scanner.nextLine();
            if (Cli.BYE.equals(msg)){
                System.out.println("bye! my best friend");
                System.exit(1);
            }
            String[] args = msg.split(" ");
            if (args.length == 0){
                continue;
            }
            switch (args[0]){
                case Cli.LOGIN:
                    login(args);break;
                case Cli.P2P_CHAT:
                    p2pChat(args);break;
                default:
                    showCommand();
                    break;
            }
        }
    }

    private void showCommand(){

    }

    private void next(){
        System.out.println(Cli.NEXT);
    }

    private void p2pChat(String[] args) {
    }

    private void login(String[] args) {
        int length = 3;
        if (length == args.length){
            String userName = args[1];
            String password = args[2];
            Payload payload = loginFunc.login(userName, password);
            if (payload.isSuccess()){
                System.out.println("login success");
            }else {
                System.out.println("Login failed cause: " + payload.getMsg());
            }
            next();
        }else {
            showCommand();
        }

    }

    private interface Cli{

        String NEXT = "byteChat>";

        String BYE = "bye";

        String LOGIN = "lo";

        String P2P_CHAT = "pc";
    }
}
