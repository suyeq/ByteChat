package io.bytechat.service;

/**
 * @author : denglinhai
 * @date : 15:52 2020/3/29
 */
public interface ImService {

    String LOGIN = "login";

    String REGISTER = "register";

    String P2P_MSG = "p2pMsg";

    String GROUP_MSG = "groupMsg";

    String RECV_MSG = "recvMsg";

    String GET_ONLINE_USER = "getOnlineUser";

    String USER_OFFLINE = "userOffline";

    String FRIEND_ADD = "addFriend";

    String GROUP_ADD = "addGroup";

    String GROUP_JOIN = "joinGroup";

    String MSG_NOTICE = "msgNotice";
}
