package com.eipulse.teamproject.webSocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WebSocketUtils {

    private static Map<String, Set<WebSocketSession>> dest2Sessions = new HashMap();

    public static void addSession(String dest, WebSocketSession session) {
        dest2Sessions.computeIfAbsent(dest, k -> new HashSet()).add(session);
    }

    public static void removeSession(String dest, WebSocketSession session) {
        if (dest2Sessions.containsKey(dest)) {
            dest2Sessions.get(dest).remove(session);
        }
    }

    public static int getSubscribersCount(String dest) {
        return dest2Sessions.getOrDefault(dest, new HashSet()).size();
    }

}