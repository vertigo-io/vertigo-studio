package io.vertigo.shell.server;

import static io.vertigo.shiny.components.data.tree.ShinyIcon.FILE;
import static io.vertigo.shiny.components.data.tree.ShinyIcon.FOLDER_OPEN;

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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.vertigo.shell.Shell;
import io.vertigo.shell.systems.core.commands.uptime.UptimeCommand;
import io.vertigo.shell.systems.env.commands.list.EnvListCommand;
import io.vertigo.shell.systems.file.commands.ls.FileLsCommand;
import io.vertigo.shell.systems.file.commands.pwd.FilePwdCommand;
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

	public static record LiveComponent(String id, String action, int value) {
	}

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
				case "ls":
					sendMessage(webSocket,
							"table",
							mapper.writeValueAsString(new FileLsCommand().build()));
					break;
				case "pwd":
					sendMessage(webSocket,
							"textPath",
							mapper.writeValueAsString(new FilePwdCommand().build()));
					break;
				case "env list":
					sendMessage(webSocket,
							"table",
							mapper.writeValueAsString(new EnvListCommand().build()));
					break;
				case "uptime":
					sendMessage(webSocket,
							"paragraph",
							mapper.writeValueAsString(new UptimeCommand().build()));
					break;
				case "xcontainer":
					ObjectNode child1Data = mapper.createObjectNode()
							.put("type", "sparkLine")
							.putPOJO("data", Shiny.sparkline()
									.withTitle("Ventes par produit")
									.withValues(156, 450, 300, 200, 100, 23)
									.build());
					ObjectNode child2Data = mapper.createObjectNode()
							.put("type", "list")
							.putPOJO("data", Shiny.list()
									.withTitle("planetes")
									.withType(ShinyListType.DASHED)
									.addItem("Uranus")
									.addItem("Saturn")
									.addItem("Venus")
									.build());
					ArrayNode children = mapper.createArrayNode()
							.add(child1Data)
							.add(child2Data);

					ObjectNode containerData = mapper.createObjectNode()
							.put("title", "premier conteneur")
							.set("children", children);

					sendMessage(webSocket, "container", mapper.writeValueAsString(containerData));
					break;
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
				case "xbar":
					var barchart = Shiny.barChart()
							.withTitle("Ventes par produit")
							.withHeader("telephones", "ordinateurs", "livres")
							.withValues(156, 34, 55)
							.build();
					sendMessage(webSocket, "barChart", mapper.writeValueAsString(barchart));
					break;
				case "xgauge":
					var gauge = Shiny.gauge()
							.withTitle("Ventes par produit")
							.withValue(156)
							.withMaxValue(450)
							.build();
					sendMessage(webSocket, "gauge", mapper.writeValueAsString(gauge));
					break;
				case "xspark":
					var sparkLine = Shiny.sparkline()
							.withTitle("Ventes par produit")
							.withValues(156, 450, 300, 200, 100, 23)
							.build();
					sendMessage(webSocket, "sparkLine", mapper.writeValueAsString(sparkLine));
					break;
				case "xmap":
					var geoMap = """
							{
							"title" : "Tour Eiffel",
							"latitude":48.8584,
							"longitude":2.2945
							}
							""";
					sendMessage(webSocket, "geoMap", geoMap);
					break;
				case "xsankey":
					final var jsonSankey = """
														{
														"title": "Flux d'énergie",
														"data": [
							  { "from": "Nucléaire", "to": "Réseau électrique", "flow": 120 },
							  { "from": "Hydraulique", "to": "Réseau électrique", "flow": 80 },
							  { "from": "Éolien", "to": "Réseau électrique", "flow": 60 },
							  { "from": "Solaire", "to": "Réseau électrique", "flow": 40 },
							  { "from": "Charbon", "to": "Réseau électrique", "flow": 100 },

							  { "from": "Réseau électrique", "to": "Industrie", "flow": 150 },
							  { "from": "Réseau électrique", "to": "Transport", "flow": 70 },
							  { "from": "Réseau électrique", "to": "Résidentiel", "flow": 100 },
							  { "from": "Réseau électrique", "to": "Pertes réseau", "flow": 20 },

							  { "from": "Résidentiel", "to": "Chauffage", "flow": 40 },
							  { "from": "Résidentiel", "to": "Électroménager", "flow": 30 },
							  { "from": "Résidentiel", "to": "Informatique", "flow": 30 }
							]
														}
														""";
					sendMessage(webSocket, "sankey", jsonSankey);
					break;
				case "xtree":
					var tree = Shiny.tree("Files").build();
					tree.getRoot().addChild("src", FOLDER_OPEN)
							.addChild("main", FOLDER_OPEN)
							.addChild("file.txt", FILE);
					sendMessage(webSocket, "tree", mapper.writeValueAsString(tree));
					break;
				case "xpb":
					String id;
					var progressBar = Shiny.progressBar().withTotal(10).build();
					id = progressBar.id;
					sendMessage(webSocket, "progressBar",
							mapper.writeValueAsString(progressBar));
					for (int i = 0; i <= 10; i += 1) {
						sendMessage(webSocket, "live",
								mapper.writeValueAsString(new LiveComponent(progressBar.id, "update", i + 1)));
						//							progressBar.liveUpdate(i + 1);
						try {
							Thread.sleep(500);
						} catch (final InterruptedException e) {
							Thread.currentThread().interrupt();
						}
					}
					sendMessage(webSocket, "live",
							mapper.writeValueAsString(new LiveComponent(id, "complete", -1)));
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
				case "xyoutube":
					ObjectNode youtubeData = mapper.createObjectNode()
							.put("title", "Rick Astley - Never Gonna Give You Up")
							.put("videoId", "dQw4w9WgXcQ");
					sendMessage(webSocket, "youtube", mapper.writeValueAsString(youtubeData));
					break;
				case "xspotify":
					ObjectNode spotifyData = mapper.createObjectNode()
							.put("title", "The Beatles - Here Comes The Sun")
							.put("uri", "spotify:track:0MjJ0s0J80000000000000"); // Placeholder URI
					sendMessage(webSocket, "spotify", mapper.writeValueAsString(spotifyData));
					break;
				case "xphoto":
					ObjectNode photoData = mapper.createObjectNode()
							.put("title", "Random image from picsum")
							.put("url", "https://picsum.photos/800/600")
							.put("alt", "Random image from picsum");
					sendMessage(webSocket, "photo", mapper.writeValueAsString(photoData));
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

	private String buildMessage(String type, String data) {
		return String.format("""
				{
				"type": "%s",
				"data": %s
				}
				""", type, data);
	}

	private void sendMessage(WebSocket conn, String type, String data) {
		final String json = buildMessage(type, data);
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
