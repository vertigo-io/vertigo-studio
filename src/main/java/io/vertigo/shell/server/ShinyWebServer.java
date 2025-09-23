package io.vertigo.shell.server;

import static io.vertigo.shiny.components.data.tree.ShinyIcon.FILE;
import static io.vertigo.shiny.components.data.tree.ShinyIcon.FOLDER_OPEN;

import java.net.InetSocketAddress;
import java.net.URL;
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
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.Shell;
import io.vertigo.shell.systems.core.commands.ip.IpCommand;
import io.vertigo.shell.systems.core.commands.uptime.UptimeCommand;
import io.vertigo.shell.systems.db.commands.connect.DbConnectCommand;
import io.vertigo.shell.systems.db.commands.disconnect.DbDisconnectCommand;
import io.vertigo.shell.systems.db.commands.load.DbLoadCommand;
import io.vertigo.shell.systems.db.commands.read.DbReadCommand;
import io.vertigo.shell.systems.db.commands.show.DbShowModelCommand;
import io.vertigo.shell.systems.db.commands.show.DbShowTablesCommand;
import io.vertigo.shell.systems.env.commands.list.EnvListCommand;
import io.vertigo.shell.systems.file.commands.ls.FileLsCommand;
import io.vertigo.shell.systems.file.commands.pwd.FilePwdCommand;
import io.vertigo.shell.systems.java.commands.load.JavaLoadCommand;
import io.vertigo.shell.systems.java.commands.show.JavaShowModelCommand;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.core.error.ShinyError;
import io.vertigo.shiny.components.data.json.ShinyJson;
import io.vertigo.shiny.components.data.list.ShinyList;
import io.vertigo.shiny.components.data.list.ShinyListType;
import io.vertigo.shiny.components.data.table.ShinyTable;
import io.vertigo.shiny.components.data.tree.ShinyTree;
import io.vertigo.shiny.components.dataviz.barchart.ShinyBarChart;
import io.vertigo.shiny.components.dataviz.gauge.ShinyGauge;
import io.vertigo.shiny.components.dataviz.sparkline.ShinySparkline;
import io.vertigo.shiny.components.media.rss.ShinyRssData;
import io.vertigo.shiny.components.media.rss.ShinyRssItem;
import io.vertigo.shiny.components.text.paragraph.ShinyParagraph;
import io.vertigo.shiny.components.text.textpath.ShinyTextPath;
import io.vertigo.shiny.components.text.figlet.ShinyFiglet;
import io.vertigo.shiny.components.text.title.ShinyTitle;

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
					sendMessage(webSocket, new FileLsCommand().build());
					break;
				case "pwd":
					sendMessage(webSocket, new FilePwdCommand().build());
					break;
				case "env list":
					sendMessage(webSocket, new EnvListCommand().build());
					break;
				case "uptime":
					sendMessage(webSocket, new UptimeCommand().build());
					break;
				case "ip":
					sendMessage(webSocket, new IpCommand().build());
					break;
				case "db connect":
					sendMessage(webSocket, new DbConnectCommand().build());
					break;
				case "db disconnect":
					sendMessage(webSocket, new DbDisconnectCommand().build());
					break;
				case "db load":
					sendMessage(webSocket, new DbLoadCommand().build());
					break;
				case "db read":
					sendMessage(webSocket, new DbReadCommand().build());
					break;
				case "db show tables":
					sendMessage(webSocket, new DbShowTablesCommand().build());
					break;
				case "db show model":
					sendMessage(webSocket, new DbShowModelCommand().build());
					break;
				//				case "db describe":
				//					sendMessage(webSocket, new DbDescribeCommand().build());
				//					break;
				case "java load":
					sendMessage(webSocket, new JavaLoadCommand().build());
					break;
				case "java show model":
					sendMessage(webSocket, new JavaShowModelCommand().build());
					break;
				case "rss":
					try {
						final URL feedUrl = new URL("https://www.francetvinfo.fr/titres.rss");
						final SyndFeedInput input = new SyndFeedInput();
						final SyndFeed feed = input.build(new XmlReader(feedUrl));
						List<ShinyRssItem> items = new ArrayList<>();
						for (final SyndEntry entry : feed.getEntries()) {
							ShinyRssItem item = new ShinyRssItem(
									entry.getTitle(),
									entry.getLink(),
									entry.getDescription().getValue(),
									entry.getEnclosures().getFirst().getUrl(),
									entry.getPublishedDate().toString(),
									entry.getAuthor());
							items.add(item);
						}
						ShinyRssData rssData = new ShinyRssData(feed.getTitle(), items);
						sendMessage(webSocket, "rssFeed", mapper.writeValueAsString(rssData));
					} catch (final Exception e) {
						e.printStackTrace();
						sendMessage(webSocket, "text", "\"Erreur lors de la récupération du flux RSS : " + e.getMessage() + "\"");
					}
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
					sendMessage(webSocket, table);
					break;
				case "xbar":
					var barchart = Shiny.barChart()
							.withTitle("Ventes par produit")
							.withHeader("telephones", "ordinateurs", "livres")
							.withValues(156, 34, 55)
							.build();
					sendMessage(webSocket, barchart);
					break;
				case "xgauge":
					var gauge = Shiny.gauge()
							.withTitle("Ventes par produit")
							.withValue(156)
							.withMaxValue(450)
							.build();
					sendMessage(webSocket, gauge);
					break;
				case "xspark":
					var sparkLine = Shiny.sparkline()
							.withTitle("Ventes par produit")
							.withValues(156, 450, 300, 200, 100, 23)
							.build();
					sendMessage(webSocket, sparkLine);
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
					sendMessage(webSocket, tree);
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
					sendMessage(webSocket, list);
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
					sendMessage(webSocket, json);
					break;
				case "xyoutube":
					ObjectNode youtubeData = mapper.createObjectNode()
							.put("title", "Rick Astley - Never Gonna Give You Up")
							.put("videoId", "dQw4w9WgXcQ");
					sendMessage(webSocket, "youtube", mapper.writeValueAsString(youtubeData));
					break;
				case "xphoto":
					ObjectNode photoData = mapper.createObjectNode()
							.put("title", "Random image from picsum")
							.put("url", "https://picsum.photos/800/600")
							.put("alt", "Random image from picsum");
					sendMessage(webSocket, "photo", mapper.writeValueAsString(photoData));
					break;
				case "xfiglet":
					var figlet = Shiny.figlet()
							.withText("Hello Vertigo")
							.build();
					sendMessage(webSocket, figlet);
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

	private void sendMessage(WebSocket webSocket, ShinyComponent component) {
		Assertion.check()
				.isNotNull(webSocket)
				.isNotNull(component);
		//---
		final String type = switch (component) {
			case ShinyTable c -> "table";
			case ShinyTextPath c -> "textPath";
			case ShinyParagraph c -> "paragraph";
			case ShinyTitle c -> "title";
			case ShinyError c -> "error";
			case ShinyList c -> "list";
			case ShinyJson c -> "json";
			case ShinyBarChart c -> "barChart";
			case ShinyGauge c -> "gauge";
			case ShinySparkline c -> "sparkLine";
			case ShinyFiglet c -> "figlet";
			case ShinyTree c -> "tree";
			default -> throw new IllegalArgumentException("Unknown component type: " + component.getClass());
		};
		try {
			sendMessage(webSocket, type, mapper.writeValueAsString(component));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(WebSocket webSocket, String type, String data) {
		final String json = buildMessage(type, data);
		System.out.println("send : " + json);
		webSocket.send(json);
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
