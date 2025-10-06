package io.vertigo.shell.server;

import static io.vertigo.shiny.components.data.tree.ShinyIcon.FILE;
import static io.vertigo.shiny.components.data.tree.ShinyIcon.FOLDER_OPEN;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.server.commands.ChartSamples.AreaSample;
import io.vertigo.shell.server.commands.ChartSamples.BarSample;
import io.vertigo.shell.server.commands.ChartSamples.DonutSample;
import io.vertigo.shell.server.commands.ChartSamples.LineSample;
import io.vertigo.shell.server.commands.ChartSamples.PieSample;
import io.vertigo.shell.server.commands.ChartSamples.PieSample2;
import io.vertigo.shell.server.commands.ChartSamples.RadarSample;
import io.vertigo.shell.server.commands.FormSamples.FormSample1;
import io.vertigo.shell.server.commands.FormSamples.FormSample2;
import io.vertigo.shell.server.commands.FormSamples.FormSample3;
import io.vertigo.shell.server.commands.FormSamples.FormSample4;
import io.vertigo.shell.server.commands.FormSamples.FormSample5;
import io.vertigo.shell.server.commands.FormSamples.FormSample6;
import io.vertigo.shell.server.commands.MediaSamples.PdfSample;
import io.vertigo.shell.server.commands.MediaSamples.RssSample;
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
import io.vertigo.shiny.ShinyType;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.data.card.ShinyCardFormat;
import io.vertigo.shiny.components.data.list.ShinyListType;
import io.vertigo.shiny.components.dataviz.chart.ShinyChart;
import io.vertigo.shiny.components.dataviz.rating.ShinyRatingScale;
import io.vertigo.shiny.components.dataviz.status.ShinyStatusType;
import io.vertigo.shiny.components.media.geomap.ShinyGeoPoint;

final class BansheeHandler {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static record LiveComponent(String id, String action, int value) {
	}

	void handle(Consumer<String> webSocket, String message) {
		try {
			if (message.startsWith("llm")) {
				ShinyComponent sc = new HalloweenCommand().llm(message.substring(3));
				sendMessage(webSocket, sc);
				return;
			}

			ShinyComponent component = switch (message) {
				case "ls" -> new FileLsCommand().build();
				case "pwd" -> new FilePwdCommand().build();
				case "env list" -> new EnvListCommand().build();
				case "uptime" -> new UptimeCommand().build();
				case "ip" -> new IpCommand().build();
				case "db connect" -> new DbConnectCommand().build();
				case "db disconnect" -> new DbDisconnectCommand().build();
				case "db load" -> new DbLoadCommand().build();
				case "db read" -> new DbReadCommand().build();
				case "db show tables" -> new DbShowTablesCommand().build();
				case "db show model" -> new DbShowModelCommand().build();
				case "java load" -> new JavaLoadCommand().build();
				case "java show model" -> new JavaShowModelCommand().build();
				case "bar" -> new BarSample().execute();
				case "pie" -> new PieSample().execute();
				case "pie2" -> new PieSample2().execute();
				case "donut" -> new DonutSample().execute();
				case "area" -> new AreaSample().execute();
				case "line" -> new LineSample().execute();
				case "radar" -> new RadarSample().execute();
				case "f1" -> new FormSample1().execute();
				case "f2" -> new FormSample2().execute();
				case "f3" -> new FormSample3().execute();
				case "f4" -> new FormSample4().execute();
				case "f5" -> new FormSample5().execute();
				case "f6" -> new FormSample6().execute();
				case "figlet" -> Shiny.figlet()
						.withText("Hello Vertigo")
						.build();
				case "textpath", "tp" -> Shiny.textPath()
						.withPath("root/node/leaf")
						.withSeparator("/")
						.build();
				case "error" -> Shiny.error()
						.withText("This is an error message")
						.build();
				case "title" -> Shiny.title()
						.withText("This is a title")
						.withLevel(2)
						.build();
				case "paragraph" -> Shiny.paragraph()
						.withText("This is a paragraph.")
						.build();
				case "gauge" -> Shiny.gauge()
						.withTitle("Ventes par produit")
						.withValue(156)
						.withMaxValue(450)
						.build();
				case "sparkline" -> Shiny.sparkline()
						.withTitle("S.p.a.r.k.l.i.n.e")
						.withValues(156, 450, 300, 200, 100, 23)
						.build();
				case "photo" -> Shiny.photo()
						.withTitle("Random image from picsum")
						.withUrl("https://picsum.photos/800/600")
						.withAlt("Random image from picsum")
						.build();
				case "card" -> Shiny.card()
						.withTitle("Mon Titre de Carte")
						.withSubtitle("Un sous-titre pour le contexte")
						.withDescription("Ceci est le contenu principal de ma carte. Il peut être plus long et contenir des informations détaillées sur le sujet de la carte.")
						.withImageUrl("https://picsum.photos/id/237/200/300")
						.withImageAlt("Image aléatoire de Picsum")
						.withLink("https://www.vertigo.io")
						.withIcon("star") // Exemple d'icône Lucide
						.withBadge("Nouveau", "blue")
						.withFormat(ShinyCardFormat.M) // Format de la carte (S, M, L)
						.build();
				case "list" -> Shiny.list()
						.withTitle("planetes")
						.withType(ShinyListType.UNORDERED)
						.addItem("Uranus")
						.addList(Shiny.list()
								.withTitle("Mars")//<< Item 
								.withType(ShinyListType.UNORDERED)
								.addItem("Bleue")
								.addItem("Rouge")
								.addItem("Verte")
								.build())
						.addItem("Saturn")
						.addItem("Venus")
						.build();
				case "status" -> Shiny.status()
						.withTitle("Component Status")
						.addType(ShinyStatusType.SUCCESS)
						.addType(ShinyStatusType.ERROR)
						.addType(ShinyStatusType.WARNING)
						.build();
				case "rating" -> Shiny.rating()
						.withLabel("User satisfaction")
						.withValue(3.5)
						.withScale(ShinyRatingScale.SCALE_5)
						.withAllowHalfRating(true)
						.build();
				case "json" -> Shiny.json()
						.withJson("""
								{
								  "title": "The Shining",
								  "director": "Stanley Kubrick",
								  "release_year": 1980,
								  "genre": ["Horror", "Thriller"],
								  "duration": "2h 26m",
								  "cast": ["Jack Nicholson", "Shelley Duvall", "Danny Lloyd"],
								  "synopsis": "A family heads to an isolated hotel for the winter where a sinister presence influences the father into violence."
								}
								""")
						.withTitle("Fiche de Shinning")
						.build();
				case "json2" -> Shiny.json()
						.withJson("""
								{
								  "title": "Barry Lindon",
								  "director": "Stanley Kubrick"
								}
								""")
						.withTitle("Fiche de Barry Lindon")
						.build();
				case "youtube" -> Shiny.youtube()
						.withTitle("Rick Astley - Never Gonna Give You Up")
						.withVideoId("dQw4w9WgXcQ")
						.build();
				case "map" -> Shiny.geoMap()
						.withTitle("Tour Eiffel & Saint Germain")
						.addGeoPoint(ShinyGeoPoint.of(48.8584, 2.2945, "Tour Eiffel"))
						.addGeoPoint(ShinyGeoPoint.of(48.901022, 2.100765, "Saint Germain en Laye"))
						.build();
				case "rss" -> new RssSample().execute();
				case "pdf" -> new PdfSample().execute();
				default -> null;
			};

			if (component != null) {
				sendMessage(webSocket, component);
				return;
			}

			switch (message) {
				case "xcontainer":
					ObjectNode child1Data = MAPPER.createObjectNode()
							.put("type", "sparkLine")
							.putPOJO("data", Shiny.sparkline()
									.withTitle("Ventes par produit")
									.withValues(156, 450, 300, 200, 100, 23)
									.build());
					ObjectNode child2Data = MAPPER.createObjectNode()
							.put("type", "list")
							.putPOJO("data", Shiny.list()
									.withTitle("planetes")
									.withType(ShinyListType.DASHED)
									.addItem("Uranus")
									.addItem("Saturn")
									.addItem("Venus")
									.build());
					ArrayNode children = MAPPER.createArrayNode()
							.add(child1Data)
							.add(child2Data);

					ObjectNode containerData = MAPPER.createObjectNode()
							.put("title", "premier conteneur")
							.set("children", children);

					sendMessage(webSocket, BansheeAction.create, "container", MAPPER.writeValueAsString(containerData));
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
				                case "xsankey":
									var sankey = Shiny.sankey()
											.withTitle("Flux d'énergie")
											.addLink("Nucléaire", "Réseau électrique", 120.0)
											.addLink("Hydraulique", "Réseau électrique", 80.0)
											.addLink("Éolien", "Réseau électrique", 60.0)
											.addLink("Solaire", "Réseau électrique", 40.0)
											.addLink("Charbon", "Réseau électrique", 100.0)
											.addLink("Réseau électrique", "Industrie", 150.0)
											.addLink("Réseau électrique", "Transport", 70.0)
											.addLink("Réseau électrique", "Résidentiel", 100.0)
											.addLink("Réseau électrique", "Pertes réseau", 20.0)
											.addLink("Résidentiel", "Chauffage", 40.0)
											.addLink("Résidentiel", "Électroménager", 30.0)
											.addLink("Résidentiel", "Informatique", 30.0)
											.build();
									sendMessage(webSocket, sankey);
									break;				case "xtree":
					var tree = Shiny.tree("Files").build();
					tree.getRoot().addChild("src", FOLDER_OPEN)
							.addChild("main", FOLDER_OPEN)
							.addChild("file.txt", FILE);
					sendMessage(webSocket, tree);
					break;
				case "xpb":
					String id;
					var progressBar = Shiny.progressBar()
							.withTotal(10)
							.build();
					id = progressBar.id;
					sendMessage(webSocket, BansheeAction.create, "progressBar",
							MAPPER.writeValueAsString(progressBar));
					for (int i = 0; i <= 10; i += 1) {
						sendMessage(webSocket, BansheeAction.create, "live",
								MAPPER.writeValueAsString(new LiveComponent(progressBar.id, "update", i + 1)));
						//							progressBar.liveUpdate(i + 1);
						try {
							Thread.sleep(500);
						} catch (final InterruptedException e) {
							Thread.currentThread().interrupt();
						}
					}
					sendMessage(webSocket, BansheeAction.create, "live",
							MAPPER.writeValueAsString(new LiveComponent(id, "complete", -1)));
					break;
				case "xtree2":
					var chakraTree = Shiny.tree("Chakra Tree").build();
					chakraTree.getRoot().addChild("src", FOLDER_OPEN)
							.addChild("main", FOLDER_OPEN)
							.addChild("file.txt", FILE);
					sendMessage(webSocket, chakraTree);
					break;
				case "xtable2":
					final List<String[]> chakraRows = new ArrayList<>();
					chakraRows.add(new String[] { "Chakra", "UI" });
					chakraRows.add(new String[] { "React", "Components" });
					var chakraTable = Shiny.table()
							.withTitle("Chakra Table")
							.withNoDataFound("No Chakra data found")
							.withHeader("Framework", "Library")
							.withSortable(true)
							.addAllRows(chakraRows)
							.build();
					sendMessage(webSocket, chakraTable);
					break;
				case "xwait":
					Thread.sleep(2000);
					sendMessage(webSocket, Shiny.figlet().withText("attente 2s").build());
				default:
					sendMessage(webSocket, BansheeAction.create, "text", "nada");
			}
		} catch (

		Exception e) {
			e.printStackTrace();
			//			System.out.println("Erreur de parsing : " + e.getMessage());
			//			// Envoie une erreur au client
			//			session.getBasicRemote().sendText("{\"type\": \"error\", \"message\": \"Erreur de traitement : " + e.getMessage() + "\"}");
		}
	}

	private static String buildMessage(BansheeAction action, final String type, UUID id, String data) {
		//		var message = new BansheeMessage(action, type, id, data);
		//		try {
		//			return MAPPER.writeValueAsString(message);
		//		} catch (JsonProcessingException e) {
		//			throw new VSystemException(e, "Error while using Jackson");
		//		}
		return String.format("""
				{
				"action": "%s",
				"type": "%s",
				"id": "%s",
				"data": %s
				}
				""", action, type, id, data);

	}

	private static void sendMessage(Consumer<String> webSocket, ShinyComponent component) {
		Assertion.check()
				.isNotNull(webSocket)
				.isNotNull(component);
		//---
		final String type = getType(component);
		try {
			var data = MAPPER.writeValueAsString(component);
			if (data.contains("arry")) {
				//TEST 
				//TEST 
				//TEST 
				//TEST 
				//TEST 
				sendMessage(webSocket, BansheeAction.update, type, data);
			} else {
				sendMessage(webSocket, BansheeAction.create, type, data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getType(ShinyComponent component) {
		Assertion.check().isNotNull(component);
		//---
		final ShinyType shinyType = component.getClass().getAnnotation(ShinyType.class);

		if (shinyType == null) {
			throw new IllegalArgumentException("Unknown component type: " + component.getClass());
		}
		if (component instanceof ShinyChart c) {
			// Special handling for ShinyChart 
			//to get the specific chart type
			return switch (c.chartType()) {
				case bar -> "barChart";
				case area -> "areaChart";
				case line -> "lineChart";
				case radar -> "radarChart";
				case donut -> "donutChart";
				case pie -> "pieChart";
			};
		} else {
			return shinyType.value();
		}
	}

	private static UUID last = null;

	private static void sendMessage(
			Consumer<String> webSocket,
			BansheeAction action,
			String type,
			String data) {
		UUID id = null;
		if (action == BansheeAction.create) {
			id = UUID.randomUUID();
			last = id;
		} else {
			id = last;
		}
		final String json = buildMessage(action, type, id, data);
		System.out.println(">>> send : " + json);
		webSocket.accept(json);
	}
}
