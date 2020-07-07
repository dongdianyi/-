package com.example.common;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;


public class JWebSocketClient extends WebSocketClient {

    public boolean isError=false;

    public JWebSocketClient(URI serverUri) {
        super(serverUri, new Draft_6455());
    }
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        new LogUtil<>("JWebSocketClient", "onOpen()");
        isError=false;
    }

    @Override
    public void onMessage(String message) {
        new LogUtil<>("JWebSocketClient", "onMessage()");

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        new LogUtil<>("JWebSocketClient", reason+"onClose()");

    }

    @Override
    public void onError(Exception ex) {
        new LogUtil<>("JWebSocketClient", ex.getMessage()+"onError()");
        isError = true;
    }

}
