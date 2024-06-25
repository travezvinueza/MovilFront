package com.ricardo.front.viewmodel;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketClient extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private UsuarioViewModel usuarioViewModel;

    public WebSocketClient(UsuarioViewModel viewModel) {
        this.usuarioViewModel = viewModel;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        webSocket.send("Hello, it's WebSocket!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        usuarioViewModel.refreshUsuarios(); // Actualiza la lista de usuarios cuando se recibe un mensaje
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        onMessage(webSocket, bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }
}
