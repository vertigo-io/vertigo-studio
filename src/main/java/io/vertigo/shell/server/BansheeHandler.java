package io.vertigo.shell.server;

import static io.vertigo.shiny.components.data.tree.ShinyIcon.FILE;
import static io.vertigo.shiny.components.data.tree.ShinyIcon.FOLDER_OPEN;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import io.vertigo.core.lang.Assertion;
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
import io.vertigo.shiny.components.card.ShinyCardComponent;
import io.vertigo.shiny.components.card.ShinyCardFormat;
import io.vertigo.shiny.components.core.container.ShinyContainer;
import io.vertigo.shiny.components.core.error.ShinyError;
import io.vertigo.shiny.components.data.json.ShinyJson;
import io.vertigo.shiny.components.data.list.ShinyList;
import io.vertigo.shiny.components.data.list.ShinyListType;
import io.vertigo.shiny.components.data.table.ShinyTable;
import io.vertigo.shiny.components.data.tree.ShinyTree;
import io.vertigo.shiny.components.dataviz.chart.ShinyChart;
import io.vertigo.shiny.components.dataviz.gauge.ShinyGauge;
import io.vertigo.shiny.components.dataviz.rating.ShinyRating;
import io.vertigo.shiny.components.dataviz.rating.ShinyRatingScale;
import io.vertigo.shiny.components.dataviz.sparkline.ShinySparkline;
import io.vertigo.shiny.components.dataviz.status.ShinyStatus;
import io.vertigo.shiny.components.dataviz.status.ShinyStatusType;
import io.vertigo.shiny.components.form.ShinyForm;
import io.vertigo.shiny.components.form.ShinyFormField;
import io.vertigo.shiny.components.form.ShinyFormFieldType;
import io.vertigo.shiny.components.form.ShinyFormFieldValidator;
import io.vertigo.shiny.components.form.ShinyFormOption;
import io.vertigo.shiny.components.media.pdf.ShinyPdfComponent;
import io.vertigo.shiny.components.media.rss.ShinyRssData;
import io.vertigo.shiny.components.media.rss.ShinyRssItem;
import io.vertigo.shiny.components.text.figlet.ShinyFiglet;
import io.vertigo.shiny.components.text.paragraph.ShinyParagraph;
import io.vertigo.shiny.components.text.textpath.ShinyTextPath;
import io.vertigo.shiny.components.text.title.ShinyTitle;

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
			switch (message) {
				//			// Parser le message JSON
				//			WebSocketMessage wsMessage = MAPPER.readValue(message, WebSocketMessage.class);
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
						sendMessage(webSocket, BansheeAction.create, "rssFeed", MAPPER.writeValueAsString(rssData));
					} catch (final Exception e) {
						e.printStackTrace();
						sendMessage(webSocket, BansheeAction.create, "text", "\"Erreur lors de la récupération du flux RSS : " + e.getMessage() + "\"");
					}
					break;
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
				case "xbar":
					var barchart = Shiny.barChart()
							.withTitle("Ventes par produit")
							.withLabels("telephones", "ordinateurs", "livres")
							.addSerie("Ventes 2023", 156.0, 34.0, 55.0)
							.addSerie("Ventes 2024", 180.0, 45.0, 65.0)
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
				case "xsparkline":
					var sparkLine = Shiny.sparkline()
							.withTitle("Chakra Sparkline")
							.withValues(156, 450, 300, 200, 100, 23)
							.build();
					sendMessage(webSocket, sparkLine);
					break;
				case "xpie":
					var pieChart = Shiny.pieChart()
							.withTitle("Répartition des ventes")
							.withLabels("Téléphones", "Ordinateurs", "Livres")
							.addSerie("Ventes 2023", 40.0, 20.0, 15.0)
							.build();
					sendMessage(webSocket, pieChart);
					break;
				case "xpie2":
					var pie2Chart = Shiny.pieChart()
							.withTitle("Répartition des ventes")
							.withLabels("Téléphones", "Ordinateurs", "Livres")
							.addSerie("Ventes 2023", 40.0, 20.0, 15.0)
							.addSerie("Ventes 2024", 45.0, 18.0, 16.0)
							.addSerie("Ventes 2025", 44.0, 16.0, 17.0)
							.build();
					sendMessage(webSocket, pie2Chart);
					break;
				case "xdonut":
					var donutChart = Shiny.donutChart()
							.withTitle("Répartition des ventes")
							.withLabels("Téléphones", "Ordinateurs", "Livres")
							.addSerie("Ventes 2023", 40.0, 20.0, 15.0)
							.addSerie("Ventes 2024", 45.0, 18.0, 16.0)
							.addSerie("Ventes 2025", 44.0, 16.0, 17.0)
							.build();
					sendMessage(webSocket, donutChart);
					break;
				case "xarea":
					var areaChart = Shiny.areaChart()
							.withTitle("Ventes par mois")
							.withLabels("Jan", "Fev", "Mar", "Avr", "Mai", "Juin")
							.addSerie("Ventes 2023", 120.0, 150.0, 170.0, 200.0, 220.0, 240.0)
							.addSerie("Ventes 2024", 130.0, 160.0, 180.0, 210.0, 230.0, 250.0)
							.build();
					sendMessage(webSocket, areaChart);
					break;
				case "xradar":
					var radarChart = Shiny.radarChart()
							.withTitle("Final Fantasy VII Stats")
							.withLabels("Attack", "Defense", "Magic Attack", "Magic Defense", "Speed", "Luck")
							.addSerie("Cloud", 85.0, 70.0, 80.0, 60.0, 90.0, 75.0)
							.addSerie("Sephiroth", 95.0, 80.0, 98.0, 85.0, 92.0, 88.0)
							.build();
					sendMessage(webSocket, radarChart);
					break;
				case "xmap":
					var geoMap = """
							{
							"title" : "Tour Eiffel",
							"latitude":48.8584,
							"longitude":2.2945
							}
							""";
					sendMessage(webSocket, BansheeAction.create, "geoMap", geoMap);
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
					sendMessage(webSocket, BansheeAction.create, "sankey", jsonSankey);
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
				case "xlist":
					var list = Shiny.list()
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
				case "xjson2":
					var json2Content = """
							{
							  "title": "Barry Lindon",
							  "director": "Stanley Kubrick"
							}
															""";
					var json2 = Shiny.json()
							.withJson(json2Content)
							.withTitle("Fiche de Barry Lindon")
							.build();
					sendMessage(webSocket, json2);
					break;
				case "xyoutube":
					ObjectNode youtubeData = MAPPER.createObjectNode()
							.put("title", "Rick Astley - Never Gonna Give You Up")
							.put("videoId", "dQw4w9WgXcQ");
					sendMessage(webSocket, BansheeAction.create, "youtube", MAPPER.writeValueAsString(youtubeData));
					break;
				case "xphoto":
					ObjectNode photoData = MAPPER.createObjectNode()
							.put("title", "Random image from picsum")
							.put("url", "https://picsum.photos/800/600")
							.put("alt", "Random image from picsum");
					sendMessage(webSocket, BansheeAction.create, "photo", MAPPER.writeValueAsString(photoData));
					break;
				case "xfiglet":
					var figlet = Shiny.figlet()
							.withText("Hello Vertigo")
							.build();
					sendMessage(webSocket, figlet);
					break;
				case "xtp":
					var textPath = Shiny.textPath()
							.withPath("root/node/leaf")
							.withSeparator("/")
							.build();
					sendMessage(webSocket, textPath);
					break;
				case "xerror":
					var error = Shiny.error()
							.withText("This is an error message")
							.build();
					sendMessage(webSocket, error);
					break;
				case "xtitle":
					var title = Shiny.title()
							.withText("This is a title")
							.withLevel(2)
							.build();
					sendMessage(webSocket, title);
					break;
				case "xparagraph":
					var paragraph = Shiny.paragraph()
							.withText("This is a paragraph.")
							.build();
					sendMessage(webSocket, paragraph);
					break;
				case "xstatus":
					var status = Shiny.status()
							.withTitle("Component Status")
							.addType(ShinyStatusType.SUCCESS)
							.addType(ShinyStatusType.ERROR)
							.addType(ShinyStatusType.WARNING)
							.build();
					sendMessage(webSocket, status);
					break;
				case "xrating":
					var rating = Shiny.rating()
							.withLabel("User satisfaction")
							.withValue(3.5)
							.withScale(ShinyRatingScale.SCALE_5)
							.withAllowHalfRating(true)
							.build();
					sendMessage(webSocket, rating);
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
					var chakraTable = Shiny.chakraTable()
							.withTitle("Chakra Table")
							.withNoDataFound("No Chakra data found")
							.withHeader("Framework", "Library")
							.withSortable(true)
							.addAllRows(chakraRows)
							.build();
					sendMessage(webSocket, chakraTable);
					break;
				case "xf1":
					var form1 = Shiny.form()
							.withTitle("Person Details")
							.addSection("Personal Info", List.of(
									new ShinyFormField("firstName", "First Name", ShinyFormFieldType.STRING, "John", true, "Enter first name", "", null, null, null, false),
									new ShinyFormField("lastName", "Last Name", ShinyFormFieldType.STRING, "Doe", true, "Enter last name", "", null, null, null, false),
									new ShinyFormField("age", "Age", ShinyFormFieldType.NUMBER, 30, false, "", "", null, null, new ShinyFormFieldValidator(null, null, null, 0, 120), false),
									new ShinyFormField("birthDate", "Birth Date", ShinyFormFieldType.DATE, "1990-01-01", false, "", "", null, null, null, false),
									new ShinyFormField("isActive", "Is Active", ShinyFormFieldType.BOOLEAN, true, false, "", "", null, null, null, false)), true, false)
							.addSection("Contact Info", List.of(
									new ShinyFormField("email", "Email", ShinyFormFieldType.STRING, "john.doe@example.com", true, "", "", null, null, new ShinyFormFieldValidator("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", null, null, null, null), false),
									new ShinyFormField("phone", "Phone", ShinyFormFieldType.STRING, "+1-555-123-4567", false, "", "", null, null, new ShinyFormFieldValidator("^\\+?[0-9]{1,3}?[ -]?[0-9]{3}?[ -]?[0-9]{3}?[ -]?[0-9]{4}$", null, null, null, null), false)), false, true)
							.build();
					sendMessage(webSocket, form1);
					break;
				case "xf2":
					var form2 = Shiny.form()
							.withTitle("Product Details")
							.addSection("Product Info", List.of(
									new ShinyFormField("productName", "Product Name", ShinyFormFieldType.STRING, "Smartphone X", true, "", "", null, null, null, false),
									new ShinyFormField("price", "Price", ShinyFormFieldType.NUMBER, 799.99, true, "", "", null, null, new ShinyFormFieldValidator(null, null, null, 0, 10000), false),
									new ShinyFormField("description", "Description", ShinyFormFieldType.TEXTAREA, "Latest model with advanced features.", false, "", "", null, null, null, false),
									new ShinyFormField("imageUrl", "Image", ShinyFormFieldType.IMAGE, "https://picsum.photos/id/237/200/300", false, "", "", null, null, null, false),
									new ShinyFormField("inStock", "In Stock", ShinyFormFieldType.BOOLEAN, true, false, "", "", null, null, null, false),
									new ShinyFormField("category", "Category", ShinyFormFieldType.SELECT, "Electronics", true, "", "", null, List.of(
											new ShinyFormOption("Electronics", "Electronics"),
											new ShinyFormOption("Books", "Books"),
											new ShinyFormOption("Home", "Home")), null, false)))
							.build();
					sendMessage(webSocket, form2);
					break;
				case "xf3":
					var form3 = Shiny.form()
							.withTitle("Photo Metadata")
							.addSection("File Info", List.of(
									new ShinyFormField("fileName", "File Name", ShinyFormFieldType.STRING, "IMG_001.jpg", true, "", "", null, null, null, true),
									new ShinyFormField("fileSize", "File Size (KB)", ShinyFormFieldType.NUMBER, 2048, false, "", "", null, null, null, true),
									new ShinyFormField("dateTaken", "Date Taken", ShinyFormFieldType.DATE, "2023-04-15", false, "", "", null, null, null, true),
									new ShinyFormField("isPublic", "Public", ShinyFormFieldType.BOOLEAN, false, false, "", "", null, null, null, false)))
							.addSection("Location", List.of(
									new ShinyFormField("latitude", "Latitude", ShinyFormFieldType.NUMBER, 34.0522, false, "", "", null, null, null, true),
									new ShinyFormField("longitude", "Longitude", ShinyFormFieldType.NUMBER, -118.2437, false, "", "", null, null, null, true)))
							.build();
					sendMessage(webSocket, form3);
					break;
				case "xf4":
					var form4 = Shiny.form()
							.withTitle("Critique de film")
							.addSection("Détails du film", List.of(
									new ShinyFormField("movie", "Film", ShinyFormFieldType.SELECT, "LOTR1", true, "", "", null, List.of(
											new ShinyFormOption("Star Wars", "Star Wars"),
											new ShinyFormOption("LOTR1", "Le Seigneur des Anneaux : La Communauté de l'anneau"),
											new ShinyFormOption("LOTR2", "Le Seigneur des Anneaux : Les Deux Tours"),
											new ShinyFormOption("LOTR3", "Le Seigneur des Anneaux : Le Retour du roi")), null, false),
									new ShinyFormField("rating", "Note", ShinyFormFieldType.NUMBER, 3, true, "", "Note sur 5", null, null, null, false),
									new ShinyFormField("review", "Critique", ShinyFormFieldType.TEXTAREA, "Un film incroyable !", false, "Écrivez votre critique ici", "", null, null, null, false)), true, false)
							.build();
					sendMessage(webSocket, form4);
					break;
				case "xf5":
					var form5 = Shiny.form()
							.withTitle("Commande de produit")
							.addSection("Détails du produit", List.of(
									new ShinyFormField("product", "Produit", ShinyFormFieldType.STRING, "T-shirt Vertigo", true, "", "", null, null, null, true),
									new ShinyFormField("size", "Taille", ShinyFormFieldType.RADIO, "M", true, "", "", null, List.of(
											new ShinyFormOption("S", "Small"),
											new ShinyFormOption("M", "Medium"),
											new ShinyFormOption("L", "Large")), null, false),
									new ShinyFormField("gift", "Emballage cadeau", ShinyFormFieldType.CHECKBOX_GROUP, true, false, "", "", null, null, null, false)), true, false)
							.build();
					sendMessage(webSocket, form5);
					break;
				case "xf6":
					var form6 = Shiny.form()
							.withTitle("Ajout de film")
							.addSection("Informations sur le film", List.of(
									new ShinyFormField("title", "Titre", ShinyFormFieldType.STRING, "Inception", true, "Titre du film", "", null, null, null, false),
									new ShinyFormField("genres", "Genres", ShinyFormFieldType.CHECKBOX_GROUP, List.of("SF", "Action"), true, "", "", null, List.of(
											new ShinyFormOption("SF", "Science-Fiction"),
											new ShinyFormOption("Action", "Action"),
											new ShinyFormOption("Drama", "Drame"),
											new ShinyFormOption("Comedy", "Comédie")), null, false)),
									true, false)
							.build();
					sendMessage(webSocket, form6);
					break;
				case "xpdf":
					try {
						var pdf = Shiny.pdf()
								.withTitle("Arthur Rimbaud - Poèmes")
								.withPath("sample-report.pdf")
								.build();
						sendMessage(webSocket, pdf);
					} catch (Exception e) {
						e.printStackTrace();
						// Send an error message to the client if download fails
						var err = Shiny.error()
								.withText("Failed to download PDF: " + e.getMessage())
								.build();
						sendMessage(webSocket, err);
					}
					break;
				case "xcard":
					var card = Shiny.card()
							.withTitle("Mon Titre de Carte")
							.withDescription("""
									Ceci est le contenu de ma carte. Il peut être plus
									long et contenir des informations détaillées.
									  """)
							.withImageUrl("https://picsum.photos/id/237/200/300")
							.withFormat(ShinyCardFormat.M)
							.build();
					sendMessage(webSocket, card);
					break;
				default:
					sendMessage(webSocket, BansheeAction.create, "text", "nada");
			}
		} catch (Exception e) {
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
		final String type = switch (component) {
			//---data
			case ShinyTable c -> "table";
			case ShinyList c -> "list";
			case ShinyJson c -> "json";
			case ShinyTree c -> "tree";

			//---text
			case ShinyTextPath c -> "textPath";
			case ShinyParagraph c -> "paragraph";
			case ShinyTitle c -> "title";
			case ShinyFiglet c -> "figlet";

			//---core
			case ShinyError c -> "error";
			case ShinyContainer c -> "container";

			//---dataviz
			case ShinyChart c -> switch (c.chartType()) {
				case bar -> "barChart";
				case area -> "areaChart";
				case radar -> "radarChart";
				case donut -> "donutChart";
				case pie -> "pieChart";
			};
			case ShinyGauge c -> "gauge";
			case ShinySparkline c -> "sparkLine";
			case ShinyStatus c -> "status";
			case ShinyRating c -> "rating";
			//---media
			case ShinyPdfComponent c -> "pdf";
			case ShinyCardComponent c -> "card";

			case ShinyForm c -> "form";
			default -> throw new IllegalArgumentException("Unknown component type: " + component.getClass());
		};
		return type;
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
