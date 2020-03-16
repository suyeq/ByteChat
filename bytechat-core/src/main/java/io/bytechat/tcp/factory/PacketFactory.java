package io.bytechat.tcp.factory;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import io.bytechat.tcp.entity.*;

/**
 * @author : denglinhai
 * @date : 14:48 2020/2/29
 */
public class PacketFactory {

    public static Packet newRequestPacket(Request request, long id){
        Packet packet = new DefaultPacket();
        packet.setId(id);
        packet.setType(Packet.PACKET_REQUEST);
        packet.setRequest(request);
        return packet;
    }

    public static Packet newResponsePacket(Payload payload, long id){
        Packet packet = new DefaultPacket();
        packet.setId(id);
        packet.setType(Packet.PACKET_RESPONSE);
        packet.setPayload(payload);
        return packet;
    }

    public static Packet newCommandPacket(Command command, long id){
        Packet packet = new DefaultPacket();
        packet.setId(id);
        packet.setType(Packet.PACKET_COMMAND);
        packet.setCommand(command);
        return packet;
    }
}