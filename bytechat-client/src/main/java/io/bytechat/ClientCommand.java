package io.bytechat;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import io.bytechat.client.Client;
import io.bytechat.entity.UserEntity;
import io.bytechat.func.BaseFunc;
import io.bytechat.func.GroupFunc;
import io.bytechat.func.LoginFunc;
import io.bytechat.func.RegisterFunc;
import io.bytechat.func.SendGroupMsgFunc;
import io.bytechat.func.SendP2pFunc;
import io.bytechat.func.UserFunc;
import io.bytechat.tcp.entity.Payload;

import java.util.List;
import java.util.Scanner;

/**
 * @author : denglinhai
 * @date : 16:03 2020/4/4
 */
public class ClientCommand {

    private BaseFunc baseFunc;

    private UserFunc userFunc;

    private LoginFunc loginFunc;

    private GroupFunc groupFunc;

    private SendP2pFunc sendP2pFunc;

    private RegisterFunc registerFunc;

    private SendGroupMsgFunc sendGroupMsgFunc;


    public ClientCommand(Client client){
        this.baseFunc = new BaseFunc(client);
        this.userFunc = new UserFunc(baseFunc);
        this.loginFunc = new LoginFunc(baseFunc);
        this.groupFunc = new GroupFunc(baseFunc);
        this.sendP2pFunc = new SendP2pFunc(baseFunc);
        this.registerFunc = new RegisterFunc(baseFunc);
        this.sendGroupMsgFunc = new SendGroupMsgFunc(baseFunc);
    }


    public void doCli(){
        Scanner scanner = new Scanner(System.in);
        //showCommand();
        while (true){
            tip();
            String msg = scanner.nextLine();
            if (Cli.BYE.equals(msg)){
                System.out.println("bye! my best friend");
                System.exit(-1);
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
                case Cli.FETCH_ONLINE_USER:
                    fetchOnlineUser(args);break;
                case Cli.GROUP_CHAT:
                    groupChat(args);break;
                case Cli.OFFLINE_USER:
                    makeOfflineUser(args);break;
                case Cli.FRIEND_ADD:
                    addFriend(args);break;
                case Cli.GROUP_CREATE:
                    createGroup(args);break;
                case Cli.GROUP_JOIN:
                    joinGroup(args);break;
                case Cli.HELP:
                    showCommand();break;
                default:
                    showCommand();
                    break;
            }
        }
    }

    private void groupChat(String[] args) {
        int length = 3;
        if (args.length >= length){
            String groupId = args[1];
            String message = args[2];
            if (!NumberUtil.isLong(groupId)){
                showCommand();
                return;
            }
            Payload payload = sendGroupMsgFunc.sendGroupMsg(Long.parseLong(groupId), message, 1, (byte)1);
            if (payload.isSuccess()){
                System.out.println("send message success");
            }else {
                System.out.println("send message failed cause:"+ payload.getMsg());
            }
        }else {
            showCommand();
        }
    }


    private void showCommand(){
        System.out.println("\t[hp]\t\t\t\t\t\t\t\t(command list)");
        System.out.println("\t[rg]\t[userName ]\t[password]\t\t(register)");
        System.out.println("\t[lo]\t[userName ]\t[password]\t\t(login)");
        System.out.println("\t[fu]\t\t\t\t\t\t\t\t(list online user)");
        System.out.println("\t[pc]\t[toUserId ]\t[message ]\t\t(p2p chat)");
        System.out.println("\t[gc]\t[groupId  ]\t[message ]\t\t(group chat)");
        System.out.println("\t[of]\t[userId   ]\t\t\t\t\t(make offline user)");
        System.out.println("\t[fa]\t[userId   ]\t\t\t\t\t(add friend)");
        System.out.println("\t[gr]\t\t\t\t\t\t\t\t(create group)");
        System.out.println("\t[gj]\t[groupId  ]\t\t\t\t\t(join group)");
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
        }else {
            showCommand();
        }
    }



    private void fetchOnlineUser(String[] args) {
        int length = 1;
        if (length == args.length){
            Payload payload = userFunc.fetchOnlineUsers();
            if (payload.isSuccess()){
                System.out.println("userName(userId)");
                List<UserEntity> userEntities =(List<UserEntity>) payload.getResult();
                if (!CollectionUtil.isEmpty(userEntities)){
                    for (UserEntity userEntity : userEntities){
                        System.out.println(userEntity.getUserName()+"("+userEntity.getId()+")");
                    }
                }
            }else {
                System.out.println("fetch online user failed cause: " + payload.getMsg());
            }
        }else {
            showCommand();
        }

    }

    private void makeOfflineUser(String[] args){
        int length = 2;
        if (length == args.length){
            String userId = args[1];
            if (!NumberUtil.isLong(userId)){
                showCommand();
                return;
            }
            Payload payload = userFunc.makeOfflineUser(Long.parseLong(args[1]));
            if (payload.isSuccess()){
                System.out.println("make user offline success");
            }else {
                System.out.println("make user offline fail,cause:" + payload.getMsg());
            }
        }
    }

    private void addFriend(String[] args){
        int length = 2;
        if (length == args.length){
            String userId = args[1];
            if (!NumberUtil.isLong(userId)){
                showCommand();
                return;
            }
            Payload payload = userFunc.addFriend(Long.parseLong(args[1]));
            if (payload.isSuccess()){
                System.out.println("add friend success");
            }else {
                System.out.println("add friend fail,cause:" + payload.getMsg());
            }
        }
    }

    private void createGroup(String[] args){
        int length = 1;
        if (length == args.length){
            Payload payload = groupFunc.createGroup();
            if (payload.isSuccess()){
                System.out.println("create group success,groupId is " + payload.getResult());
            }else {
                System.out.println("create group fail,cause:" + payload.getMsg());
            }
        }
    }

    private void joinGroup(String[] args){
        int length = 2;
        if (length == args.length){
            String userId = args[1];
            if (!NumberUtil.isLong(userId)){
                showCommand();
                return;
            }
            Payload payload = groupFunc.joinGroup(Long.parseLong(args[1]));
            if (payload.isSuccess()){
                System.out.println("join group success");
            }else {
                System.out.println("join group fail,cause:" + payload.getMsg());
            }
        }
    }

    private interface Cli{

        String NEXT = "byteChat>";

        String BYE = "bye";

        String HELP = "hp";

        String LOGIN = "lo";

        String P2P_CHAT = "pc";

        String GROUP_CHAT = "gc";

        String REGISTER = "rg";

        String FETCH_ONLINE_USER = "fu";

        String OFFLINE_USER = "of";

        String FRIEND_ADD = "fa";

        String GROUP_CREATE = "gr";

        String GROUP_JOIN = "gj";
    }
}
