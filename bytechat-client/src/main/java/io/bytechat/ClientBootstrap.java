package io.bytechat;

import cn.hutool.core.util.NumberUtil;
import io.bytechat.client.Client;
import io.bytechat.func.BaseFunc;
import io.bytechat.func.LoginFunc;
import io.bytechat.func.RegisterFunc;
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

    private RegisterFunc registerFunc;

    public ClientBootstrap(Client client){
        this.baseFunc = new BaseFunc(client);
        this.loginFunc = new LoginFunc(baseFunc);
        this.sendP2pFunc = new SendP2pFunc(baseFunc);
        this.registerFunc = new RegisterFunc(baseFunc);
    }


    public void doCli(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            showCommand();
            String msg = scanner.nextLine();
            if (Cli.BYE.equals(msg)){
                System.out.println("bye! my best friend");
                System.exit(1);
            }
            String[] args = msg.split(" ");
            if (args.length == 0){
                tip();
                continue;
            }
            switch (args[0]){
                case Cli.LOGIN:
                    login(args);break;
                case Cli.P2P_CHAT:
                    p2pChat(args);break;
                case Cli.REGISTER:
                    register(args);break;
                default:
                    showCommand();
                    break;
            }
        }
    }

    private void showCommand(){
        System.out.println("command list");
        System.out.println("\t[rg]\t[userName ]\t[password]\t\t(register)");
        System.out.println("\t[lo]\t[userName ]\t[password]\t\t(login)");
        System.out.println("\t[lu]\t\t\t\t\t\t\t\t(list online user)");
        System.out.println("\t[pc]\t[toUserId]\t[message]\t\t(p2p chat)");
        tip();
    }

    private void tip(){
        System.out.print(Cli.NEXT);
    }

    private void p2pChat(String[] args) {
        int length = 3;
        if (args.length >= length){
            String userId = args[1];
            String message = args[2];
            if (!NumberUtil.isLong(userId)){
                showCommand();
                return;
            }
            Payload payload = sendP2pFunc.sendP2pMsg(Long.parseLong(userId), 1, message, (byte)1);
            if (payload.isSuccess()){
                System.out.println("send message success");
            }else {
                System.out.println("send message failed cause:"+ payload.getMsg());
            }
        }else {
            showCommand();
        }
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
                System.out.println("login failed cause: " + payload.getMsg());
            }
            tip();
        }else {
            showCommand();
        }

    }

    private void register(String[] args) {
        int length = 3;
        if (length == args.length){
            String userName = args[1];
            String password = args[2];
            Payload payload = registerFunc.register(userName, password);
            if (payload.isSuccess()){
                System.out.println("register success");
            }else {
                System.out.println("register failed cause: " + payload.getMsg());
            }
            tip();
        }else {
            showCommand();
        }

    }

    private interface Cli{

        String NEXT = "byteChat>";

        String BYE = "bye";

        String LOGIN = "lo";

        String P2P_CHAT = "pc";

        String REGISTER = "rg";
    }
}
