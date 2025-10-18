package io.vertigo.banshee.servers;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import io.vertigo.core.lang.Assertion;

public final class BansheeWebSocketServer extends WebSocketServer {
	private static final Set<WebSocket> connections = Collections.synchronizedSet(new HashSet<>());;
	private final BansheeHandler handler;

	public BansheeWebSocketServer(int port, BansheeHandler handler) {
		super(new InetSocketAddress(port));
		Assertion.check()
				.isNotNull(handler);
		//---
		this.handler = handler;
	}

	@Override
	public void onOpen(WebSocket webSocket, ClientHandshake handshake) {
		Assertion.check()
				.isNotNull(webSocket)
				.isNotNull(handshake);
		//---
		connections.add(webSocket);
		System.out.println("New connection from " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
		final BansheePipedInputStream in = new BansheePipedInputStream();
		webSocket.setAttachment(in);
	}

	@Override
	public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {
		Assertion.check()
				.isNotNull(webSocket);
		//---
		connections.remove(webSocket);
		System.out.println("Closed connection to " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
	}

	@Override
	public void onMessage(WebSocket webSocket, String event) {
		Assertion.check()
				.isNotNull(webSocket);
		//---
		System.out.println("<<< receive : " + event);
		handler.handle((s) -> webSocket.send(s), event);
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
