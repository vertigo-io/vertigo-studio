package io.vertigo.banshee;

import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.banshee.com.BansheeAction;
import io.vertigo.banshee.com.BansheeCommand;
import io.vertigo.banshee.com.BansheeResult;
import io.vertigo.banshee.commands.ChartSamples.AreaSample;
import io.vertigo.banshee.commands.ChartSamples.BarSample;
import io.vertigo.banshee.commands.ChartSamples.DonutSample;
import io.vertigo.banshee.commands.ChartSamples.LineSample;
import io.vertigo.banshee.commands.ChartSamples.PieSample;
import io.vertigo.banshee.commands.ChartSamples.PieSample2;
import io.vertigo.banshee.commands.ChartSamples.RadarSample;
import io.vertigo.banshee.commands.FormSamples.FormSample1;
import io.vertigo.banshee.commands.FormSamples.FormSample2;
import io.vertigo.banshee.commands.FormSamples.FormSample3;
import io.vertigo.banshee.commands.FormSamples.FormSample4;
import io.vertigo.banshee.commands.FormSamples.FormSample5;
import io.vertigo.banshee.commands.FormSamples.FormSample6;
import io.vertigo.banshee.commands.HalloweenCommand;
import io.vertigo.banshee.commands.MediaSamples.PdfSample;
import io.vertigo.banshee.commands.MediaSamples.RssSample;
import io.vertigo.banshee.servers.BansheeHandler;
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
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.ShinyProp;
import io.vertigo.shiny.models.core.container.ShinyContainerBuilder;
import io.vertigo.shiny.models.core.error.ShinyErrorBuilder;
import io.vertigo.shiny.models.data.card.ShinyCardFormat;
import io.vertigo.shiny.models.data.chip.ShinyChipVariant;
import io.vertigo.shiny.models.data.list.ShinyListType;
import io.vertigo.shiny.models.data.table.ShinyTableBuilder;
import io.vertigo.shiny.models.dataviz.flow.NodeType;
import io.vertigo.shiny.models.dataviz.flow.ShinyFlowBuilder;
import io.vertigo.shiny.models.dataviz.mindmap.ShinyMindMapNodeBuilder;
import io.vertigo.shiny.models.dataviz.rating.ShinyRatingScale;
import io.vertigo.shiny.models.dataviz.status.ShinyStatusType;
import io.vertigo.shiny.models.feedback.alert.ShinyAlertType;
import io.vertigo.shiny.models.media.geomap.ShinyGeoPoint;

public final class BansheeHandlerImpl implements BansheeHandler {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public void handle(Consumer<String> webSocket, String event) {
		try {
			final BansheeCommand receivedEvent = MAPPER.readValue(event, BansheeCommand.class);
			//1.
			final ShinyModel model = execute(receivedEvent);
			//2.
			final BansheeAction action;
			action = receivedEvent.id() == null
					? BansheeAction.create
					: BansheeAction.update;
			sendEvent(webSocket, new BansheeResult(
					action,
					model));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private ShinyModel execute(BansheeCommand event) {
		if (event.command().startsWith("llm")) {
			return new HalloweenCommand().llm(event.command().substring(3));
		}
		if (event.command().equals("table2")) {
			ShinyTableBuilder tableBuilder = Shiny.table()
					.withTitle("alphabet")
					.withHeader("Lettre de A à Z");
			if (event.id() != null) {
				tableBuilder.withId(event.id());
			}
			String page = event.props() == null
					? "1"
					: event.props()
							.stream()
							.filter(p -> "page".equals(p.key()))
							.findFirst()
							.orElseGet(() -> ShinyProp.of("page", 1))
							.value();

			switch (page) {
				case "1":
					tableBuilder
							.addRow("A").addRow("B").addRow("C").addRow("D").addRow("E");
					break;
				case "2":
					tableBuilder
							.addRow("F").addRow("G").addRow("H").addRow("I").addRow("J");
					break;
				case "3":
					tableBuilder
							.addRow("K").addRow("L").addRow("M").addRow("N").addRow("O");
					break;
				case "4":
					tableBuilder
							.addRow("P").addRow("Q").addRow("R").addRow("S").addRow("T");
					break;
				case "5":
					tableBuilder
							.addRow("U").addRow("V").addRow("W").addRow("X").addRow("Y");
					break;
				case "6":
					tableBuilder
							.addRow("Z");
					break;
				default:
					tableBuilder
							.withNoDataFound("No data Found");
			}
			return tableBuilder
					.withPage(Integer.valueOf(page), 6)
					.build();
		}

		return switch (event.command()) {
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
			case "table" -> Shiny.table()
					.withTitle("carnet d'adresses")
					.withNoDataFound("no files found")
					.withHeader("Prénom", "Nom")
					.withSortable(true)
					.addRow("Arthur", "Penn")
					.addRow("Marilyn", "Pinson")
					.build();
			case "timeline", "tl" -> Shiny.timeline()
					.withTitle("Project Timeline")
					.addItem("Step 1: Conception", "Defining project goals and scope.", "blue", "mdi-lightbulb-on-outline")
					.addItem("Step 2: Development", "Building the core features.", "green", "mdi-code-braces")
					.addItem("Step 3: Testing", "Ensuring quality and stability.", "orange", "mdi-flask-empty-outline")
					.addItem("Step 4: Deployment", "Releasing to production.", "purple", "mdi-rocket-launch-outline")
					.build();
			case "slider" -> Shiny.slider()
					.withLabel("Volume")
					.withMin(0)
					.withMax(100)
					.withStep(1)
					.withValue(45)
					.withColor("orange")
					.withThumbLabel(true)
					.build();
			case "alert" -> Shiny.alert()
					.withAlertType(ShinyAlertType.INFO)
					.withTitle("Information")
					.withContent("This is an informational message.")
					.withClosable(true)
					.build();
			case "alert-success" -> Shiny.alert()
					.withAlertType(ShinyAlertType.SUCCESS)
					.withTitle("Success")
					.withContent("The operation completed successfully!")
					.build();
			case "alert-warning" -> Shiny.alert()
					.withAlertType(ShinyAlertType.WARNING)
					.withContent("This is a warning message, please be careful.")
					.build();
			case "alert-error" -> Shiny.alert()
					.withAlertType(ShinyAlertType.ERROR)
					.withTitle("Error")
					.withContent("An error occurred while processing your request.")
					.build();
			case "chip" -> Shiny.chip()
					.withText("Vuetify")
					.withColor("red")
					.withVariant(ShinyChipVariant.ELEVATED)
					.withIcon("mdi-vuetify")
					.withClosable(true)
					.build();
			case "org" -> Shiny.organization()
					.addNode("1", null, "John Doe", "CEO", "https://randomuser.me/api/portraits/men/1.jpg")
					.addNode("2", "1", "Jane Smith", "CTO", "https://randomuser.me/api/portraits/women/2.jpg")
					.addNode("3", "1", "Mike Johnson", "CFO", "https://randomuser.me/api/portraits/men/3.jpg")
					.addNode("4", "2", "Emily Brown", "Lead Developer", "https://randomuser.me/api/portraits/women/4.jpg")
					.addNode("5", "2", "David Wilson", "DevOps Engineer", "https://randomuser.me/api/portraits/men/5.jpg")
					.addNode("6", "3", "Sarah Davis", "Accountant", "https://randomuser.me/api/portraits/women/6.jpg")
					.build();
			//				case "radar2" -> Shiny.chart()
			//						.withType(ShinyChartType.radar)
			//						.withLabels("Speed", "Strength", "Stamina", "Intelligence", "Wisdom", "Charisma")
			//						.addSerie(ShinyChartSerie.of("Hero A", List.of(80, 90, 70, 60, 85, 75)))
			//						.addSerie(ShinyChartSerie.of("Hero B", List.of(70, 80, 85, 75, 60, 90)))
			//						.withColors("red", "blue")
			//						.build();
			case "pdf" -> new PdfSample().execute();
			case "tree" -> Shiny.tree()
					.withLabel("my directory")
					.addTree("Files")
					.addLeaf("src")
					.addTree("main")
					.addLeaf("file.txt")
					.up() // Go up to main
					.up() // Go up to src
					.addTree("test")
					.addLeaf("testFile.txt")
					.root()
					.build();
			case "sankey" -> Shiny.sankey()
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
			case "flow" -> new ShinyFlowBuilder()
					.withNode("1", "Order Received", 100, 50, NodeType.RR)
					.withNode("2", "Payment Processed", 300, 50, NodeType.LR)
					.withNode("3", "Items Shipped", 500, 50, NodeType.TB)
					.withNode("4", "Invoice Generated", 300, 200, NodeType.LR)
					.withNode("5", "Billing Completed", 500, 200, NodeType.LL)
					.withEdge("e1-2", "1", "2", "Process Payment")
					.withEdge("e2-3", "2", "3", "Ship Items")
					.withEdge("e2-4", "2", "4", "Generate Invoice")
					.withEdge("e4-5", "4", "5", "Finalize Billing")
					.build();
			case "wait" -> wait(Shiny.figlet().withText("attente 2s").build(), 2000);
			case "container" -> new ShinyContainerBuilder()
					.addComponent(Shiny.sparkline()
							.withTitle("Ventes par produit")
							.withValues(156, 450, 300, 200, 100, 23)
							.build())
					.addComponent(Shiny.list()
							.withTitle("planetes")
							.withType(ShinyListType.DASHED)
							.addItem("Uranus")
							.addItem("Saturn")
							.addItem("Venus")
							.build())
					.addComponent(Shiny.chip()
							.withText("Beatles")
							.withColor("red")
							.withVariant(ShinyChipVariant.ELEVATED)
							.withIcon("mdi-vuetify")
							.withClosable(true)
							.build())
					.addComponent(Shiny.chip()
							.withText("Kinks")
							.withColor("pink")
							.withVariant(ShinyChipVariant.ELEVATED)
							.withIcon("mdi-vuetify")
							.build())
					.build();
			case "mindmap" -> Shiny.mindMap()
					.withTitle("Mouvements Artistiques")
					.withRootNode(
							new ShinyMindMapNodeBuilder("root", "Mouvements Artistiques")
									.addAllChildren(
											new ShinyMindMapNodeBuilder("classicisme", "Classicisme")
													.withDirection("left")
													.addAllChildren(
															new ShinyMindMapNodeBuilder("renaissance", "Renaissance").build(),
															new ShinyMindMapNodeBuilder("baroque", "Baroque").build())
													.build(),
											new ShinyMindMapNodeBuilder("romantisme", "Romantisme")
													.withDirection("right")
													.addAllChildren(
															new ShinyMindMapNodeBuilder("pre_raphaelite", "Préraphaélisme").build(),
															new ShinyMindMapNodeBuilder("symbolisme", "Symbolisme").build())
													.build(),
											new ShinyMindMapNodeBuilder("moderne", "Art Moderne")
													.withDirection("left")
													.addAllChildren(
															new ShinyMindMapNodeBuilder("impressionnisme", "Impressionnisme").build(),
															new ShinyMindMapNodeBuilder("cubisme", "Cubisme").build())
													.build(),
											new ShinyMindMapNodeBuilder("contemporain", "Art Contemporain")
													.withDirection("right")
													.addAllChildren(
															new ShinyMindMapNodeBuilder("pop_art", "Pop Art").build(),
															new ShinyMindMapNodeBuilder("minimalisme", "Minimalisme").build())
													.build())
									.build())
					.build();
			default -> new ShinyErrorBuilder().withText("unknown command :" + event).build();
		};
	}

	private static ShinyModel wait(ShinyModel model, int timeInMillis) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return model;
	}

	private static void sendEvent(Consumer<String> webSocket, BansheeResult event) {
		Assertion.check()
				.isNotNull(webSocket)
				.isNotNull(event);
		//---
		try {
			final String json = MAPPER.writeValueAsString(event);
			webSocket.accept(json);
			System.out.println("<<< sent : " + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
