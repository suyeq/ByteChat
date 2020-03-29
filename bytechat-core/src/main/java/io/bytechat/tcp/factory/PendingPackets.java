package io.bytechat.tcp.factory;

import io.bytechat.tcp.entity.Packet;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : denglinhai
 * @date : 16:54 2020/3/20
 */
@Slf4j
public class PendingPackets {

    private static Map<Long, CompletableFuture<Packet>> pendingRequests = new ConcurrentHashMap<>();

    public static void add(Long id, CompletableFuture<Packet> promise) {
        pendingRequests.putIfAbsent(id, promise);
    }

    public static CompletableFuture<Packet> remove(Long id) {
        return pendingRequests.remove(id);
    }
}
