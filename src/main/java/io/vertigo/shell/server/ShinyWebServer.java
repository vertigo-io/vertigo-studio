package io.vertigo.shell.server;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.shell.Shell;

public class ShinyWebServer extends WebSocketServer {
	private static final Set<WebSocket> connections = Collections.synchronizedSet(new HashSet<>());

	public ShinyWebServer(int port) {
		super(new InetSocketAddress(port));
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		connections.add(conn);
		System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());

		final PipedInputStream in = new PipedInputStream();

		System.setIn(in);

		final Shell shell = new Shell();
		new Thread(shell).start();

		conn.setAttachment(in);
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		connections.remove(conn);
		System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
	}

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void onMessage(WebSocket conn, String message) {
		System.out.println("Message reçu : " + message);
		try {
			// Parser le message JSON
			WebSocketMessage wsMessage = mapper.readValue(message, WebSocketMessage.class);
			if ("prompt".equals(wsMessage.type())) {
				// Traiter la saisie utilisateur
				String userInput = wsMessage.data();
				System.out.println("Prompt reçu : " + userInput);

				if ("test".equals(userInput)) {
					sendTable(conn);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//			System.out.println("Erreur de parsing : " + e.getMessage());
			//			// Envoie une erreur au client
			//			session.getBasicRemote().sendText("{\"type\": \"error\", \"message\": \"Erreur de traitement : " + e.getMessage() + "\"}");
		}
	}

	private void sendTable(WebSocket conn) {
		String[] header = { "Nom", "Valeur" };
		List<String[]> rows = new ArrayList<>();
		rows.add(new String[] { "Item1", "100" });
		rows.add(new String[] { "Item2", "200" });
		TableData table = new TableData("Résultats", "Rien à afficher", header, rows);

		try {
			final String data = mapper.writeValueAsString(table);
			//			broadcast(json);
			final String json = String.format("""
					{
					"type": "table", "data": %s
					}
					""", data);
			System.out.println("send : " + json);
			conn.send(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		ex.printStackTrace();
	}

	@Override
	public void onStart() {
		System.out.println("ShinyWebServer started on port " + getPort());
	}

	public void broadcast(String message) {
		synchronized (connections) {
			for (WebSocket conn : connections) {
				if (conn.isOpen()) {
					conn.send(message);
				}
			}
		}
	}

	// Supposons que cette classe représente ta table
	public static record TableData(
			String tableTitle,
			String noDataFound,
			String[] tableHeader,
			List<String[]> tableRows) {
	}

	public static void main(String[] args) {
		int port = 8080; // default port
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		new ShinyWebServer(port).start();
	}
}
