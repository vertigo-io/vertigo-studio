package io.vertigo.shell.server;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

final class BansheeWebSocketServer extends WebSocketServer {
	private static final Set<WebSocket> connections = Collections.synchronizedSet(new HashSet<>());;
	private final BansheeHandler handler = new BansheeHandler();

	BansheeWebSocketServer(int port) {
		super(new InetSocketAddress(port));
	}

	@Override
	public void onOpen(WebSocket webSocket, ClientHandshake handshake) {
		connections.add(webSocket);
		System.out.println("New connection from " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
		final BansheePipedInputStream in = new BansheePipedInputStream();
		webSocket.setAttachment(in);
	}

	@Override
	public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {
		connections.remove(webSocket);
		System.out.println("Closed connection to " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
	}

	@Override
	public void onMessage(WebSocket webSocket, String message) {
		System.out.println("<<< receive : " + message);
		handler.handle((s) -> webSocket.send(s), message);
	}

	@Override
	public void onError(WebSocket webSocket, Exception ex) {
		ex.printStackTrace();
	}

	@Override
	public void onStart() {
		System.out.println("BansheeWebSocketServer started on port " + getPort());
	}

}
