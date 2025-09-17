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
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.data.list.ShinyListType;

public class ShinyWebServer extends WebSocketServer {
	private static final Set<WebSocket> connections = Collections.synchronizedSet(new HashSet<>());

	public ShinyWebServer(int port) {
		super(new InetSocketAddress(port));
	}

	@Override
	public void onOpen(WebSocket webSocket, ClientHandshake handshake) {
		connections.add(webSocket);
		System.out.println("New connection from " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());

		final PipedInputStream in = new PipedInputStream();

		System.setIn(in);

		final Shell shell = new Shell();
		new Thread(shell).start();

		webSocket.setAttachment(in);
	}

	@Override
	public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {
		connections.remove(webSocket);
		System.out.println("Closed connection to " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
	}

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void onMessage(WebSocket webSocket, String message) {
		System.out.println("Message reçu : " + message);
		try {
			switch (message) {
				//			// Parser le message JSON
				//			WebSocketMessage wsMessage = mapper.readValue(message, WebSocketMessage.class);
				//			if ("prompt".equals(wsMessage.type())) {
				//				// Traiter la saisie utilisateur
				//				String userInput = wsMessage.data();
				//				System.out.println("Prompt reçu : " + userInput);
				case "xtable":
					final List<String[]> rows = new ArrayList<>();
					rows.add(new String[] { "Arthur", "Penn" });
					rows.add(new String[] { "Marilyn", "Pinson" });
					var table = Shiny.table()
							.withTitle("carnet d'adresses")
							.withNoDataFound("no files found")
							.withHeader("Prénom", "Nom")
							.addAllRows(rows)
							.build();
					sendMessage(webSocket, "table", mapper.writeValueAsString(table));
					break;
				case "xlist":
					var list = Shiny.list()
							.withTitle("planetes")
							.withType(ShinyListType.DASHED)
							.addItem("Uranus")
							.addItem("Saturn")
							.addItem("Venus")
							.build();
					sendMessage(webSocket, "list", mapper.writeValueAsString(list));
					break;
				case "xjson":
					var jsonContent = """
							{
							  "title": "The Shining",
							  "director": "Stanley Kubrick",
							  "release_year": 1980,
							  "genre": ["Horror", "Thriller"],
							  "duration": "2h 26m",
							  "cast": ["Jack Nicholson", "Shelley Duvall", "Danny Lloyd"],
							  "synopsis": "A family heads to an isolated hotel for the winter where a sinister presence influences the father into violence."
							}
															""";
					var json = Shiny.json()
							.withJson(jsonContent)
							.withTitle("Fiche de Shinning")
							.build();
					sendMessage(webSocket, "json", mapper.writeValueAsString(json));
					break;
				default:
					sendMessage(webSocket, "text", "nada");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//			System.out.println("Erreur de parsing : " + e.getMessage());
			//			// Envoie une erreur au client
			//			session.getBasicRemote().sendText("{\"type\": \"error\", \"message\": \"Erreur de traitement : " + e.getMessage() + "\"}");
		}
	}

	private void sendMessage(WebSocket conn, String type, String data) {
		final String json = String.format("""
				{
				"type": "%s",
				"data": %s
				}
				""", type, data);
		System.out.println("send : " + json);
		conn.send(json);
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
	public static record JsonData(
			String title,
			String noDataFound,
			String json) {
	}

	//	// Supposons que cette classe représente ta table
	//	public static record TableData(
	//			String tableTitle,
	//			String noDataFound,
	//			String[] tableHeader,
	//			List<String[]> tableRows) {
	//	}

	public static void main(String[] args) {
		int port = 8080; // default port
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		new ShinyWebServer(port).start();
	}
}
