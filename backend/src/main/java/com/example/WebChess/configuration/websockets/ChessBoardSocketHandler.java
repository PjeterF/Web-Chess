package com.example.WebChess.configuration.websockets;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessBoardSocketHandler extends TextWebSocketHandler {
    private final HashMap<String, List<WebSocketSession>> rooms=new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        String url = session.getUri().toString();
        Map<String, String> queryParams=UriComponentsBuilder.fromUriString(url).build().getQueryParams().toSingleValueMap();
        session.getAttributes().put("roomID", queryParams.get("roomID"));

        System.out.println("Connected: "+session.getId());

        String roomID=(String)session.getAttributes().get("roomID");
        if(rooms.get(roomID)==null){
            rooms.put(roomID, new ArrayList<>());
        }
        rooms.get(roomID).add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

        System.out.println("Disconnected: "+session.getId());

        String roomID=(String)session.getAttributes().get("roomID");
        if(rooms.get(roomID)!=null){
            rooms.get(roomID).remove(session);
            if(rooms.get(roomID).isEmpty()){
                rooms.remove(roomID);
            }
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);

        String roomID=(String)session.getAttributes().get("roomID");
        if(rooms.get(roomID)==null){
            return;
        }
        for(var ses : rooms.get(roomID)){
            if(ses==session){
                continue;
            }
            ses.sendMessage(message);
        }
    }
}
