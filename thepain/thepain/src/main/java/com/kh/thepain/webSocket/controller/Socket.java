package com.kh.thepain.webSocket.controller;

import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import jakarta.websocket.*;
//import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint(value = "/ws/notify", configurator = HttpSessionConfigurator.class)
public class Socket {

    // 특정 사용자 전송용: memberNo → Session
    private static Map<Integer, Session> userSessions = new ConcurrentHashMap<>();

    // 접속 시 실행
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        Object memberNoObj = config.getUserProperties().get("memberNo");
        if (memberNoObj == null) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        int memberNo = (int) memberNoObj;
        // 이후 알림 전송 가능
    }

    // 접속 종료 시 실행
    @OnClose
    public void onClose(Session session) {
        // 접속 종료된 사용자 세션 제거
        userSessions.values().remove(session);
    }


    // 특정 사용자에게만 메시지 전송
    public static void sendToUser(int receiverMemberNo, String content) {
        Session session = userSessions.get(receiverMemberNo);
        if (session != null && session.isOpen()) {
            synchronized (session) { // 💡 동기화 처리 중요
                try {
                    session.getAsyncRemote().sendText(content);
                } catch (Exception e) {
                   // System.out.println("WebSocket 전송 중 오류: " + e.getMessage());
                }
            }
        } else {
           // System.out.println("세션이 닫혀있거나 없음: receiverNo = " + receiverMemberNo);
        }
    }
}
