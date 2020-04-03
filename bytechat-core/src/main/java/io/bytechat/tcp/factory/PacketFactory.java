package io.bytechat.tcp.factory;


import io.bytechat.lang.id.IdFactory;
import io.bytechat.lang.id.MemoryIdFactory;
import io.bytechat.tcp.entity.*;

/**
 * @author : denglinhai
 * @date : 14:48 2020/2/29
 */
public class PacketFactory {

    private static IdFactory idFactory = MemoryIdFactory.newInstance();

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

    public static Packet newCommandPacket(Command command){
        Packet packet = new DefaultPacket();
        //packet.setId(id);
        packet.setType(Packet.PACKET_COMMAND);
        packet.setCommand(command);
        return packet;
    }

    public static Packet newPingRequestPacket(){
        Request request = new Request();
        request.setServiceName("ping");
        Packet packet = PacketFactory.newRequestPacket(request, idFactory.nextId());
        packet.setAsyncHandle(false);
        return packet;
    }
}
