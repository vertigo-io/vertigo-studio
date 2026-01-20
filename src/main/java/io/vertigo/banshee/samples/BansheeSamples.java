package io.vertigo.banshee.samples;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.vertigo.banshee.com.BansheeCommand;
import io.vertigo.banshee.com.BansheeCommandHandler;
import io.vertigo.banshee.com.BansheeCommandHandlerBuilder;
import io.vertigo.banshee.samples.BoardSamples.CrmInstallationBoard;
import io.vertigo.banshee.samples.ChartSamples.AreaSample;
import io.vertigo.banshee.samples.ChartSamples.BarSample;
import io.vertigo.banshee.samples.ChartSamples.DonutSample;
import io.vertigo.banshee.samples.ChartSamples.LineSample;
import io.vertigo.banshee.samples.ChartSamples.PieSample;
import io.vertigo.banshee.samples.ChartSamples.PieSample2;
import io.vertigo.banshee.samples.ChartSamples.RadarSample;
import io.vertigo.banshee.samples.FormSamples.FormSample1;
import io.vertigo.banshee.samples.FormSamples.FormSample2;
import io.vertigo.banshee.samples.FormSamples.FormSample3;
import io.vertigo.banshee.samples.FormSamples.FormSample4;
import io.vertigo.banshee.samples.FormSamples.FormSample5;
import io.vertigo.banshee.samples.FormSamples.FormSample6;
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
import io.vertigo.shiny.models.block.ShinyContainerBuilder;
import io.vertigo.shiny.models.data.card.ShinyCardFormat;
import io.vertigo.shiny.models.data.list.ShinyListType;
import io.vertigo.shiny.models.data.table.ShinyTableBuilder;
import io.vertigo.shiny.models.data.table.ShinyTableStateBuilder;
import io.vertigo.shiny.models.data.table.cell.ShinyAvatarCell;
import io.vertigo.shiny.models.data.table.cell.ShinyBadgeCell;
import io.vertigo.shiny.models.data.table.cell.ShinyButtonCell;
import io.vertigo.shiny.models.data.table.cell.ShinyChipCell;
import io.vertigo.shiny.models.data.table.cell.ShinyIconCell;
import io.vertigo.shiny.models.data.table.cell.ShinyProgressBarCell;
import io.vertigo.shiny.models.data.table.cell.ShinyRatingCell;
import io.vertigo.shiny.models.data.table.cell.ShinyStringCell;
import io.vertigo.shiny.models.dataviz.flow.NodeType;
import io.vertigo.shiny.models.dataviz.flow.ShinyFlowBuilder;
import io.vertigo.shiny.models.dataviz.geomap.ShinyGeoPoint;
import io.vertigo.shiny.models.dataviz.mindmap.ShinyMindMapNodeBuilder;
import io.vertigo.shiny.models.feedback.alert.ShinyAlertType;
import io.vertigo.shiny.models.feedback.notification.ShinyNotificationType;
import io.vertigo.shiny.models.input.toggle.ShinyToggleType;
import io.vertigo.shiny.models.text.chip.ShinyChipVariant;
import io.vertigo.shiny.models.text.rating.ShinyRatingScale;
import io.vertigo.shiny.models.text.status.ShinyStatusType;

public final class BansheeSamples {
	public static final BansheeCommandHandler commandHandler = new BansheeCommandHandlerBuilder()
			.addCommandExecutor("llm", cmd -> new HalloweenCommand()
					.llm(cmd.args()))
			.addCommandExecutor("table2", BansheeSamples::table2)
			.addCommandExecutor("ls", cmd -> new FileLsCommand().build())
			.addCommandExecutor("pwd", cmd -> new FilePwdCommand().build())
			.addCommandExecutor("env list", cmd -> new EnvListCommand().build())
			.addCommandExecutor("uptime", cmd -> new UptimeCommand().build())
			.addCommandExecutor("ip", cmd -> new IpCommand().build())
			.addCommandExecutor("db connect", cmd -> new DbConnectCommand().build())
			.addCommandExecutor("db disconnect", cmd -> new DbDisconnectCommand().build())
			.addCommandExecutor("db load", cmd -> new DbLoadCommand().build())
			.addCommandExecutor("db read", cmd -> new DbReadCommand().build())
			.addCommandExecutor("db show tables", cmd -> new DbShowTablesCommand().build())
			.addCommandExecutor("db show model", cmd -> new DbShowModelCommand().build())
			.addCommandExecutor("java load", cmd -> new JavaLoadCommand().build())
			.addCommandExecutor("java show model", cmd -> new JavaShowModelCommand().build())

			.addCommandExecutor("bar", new BarSample())
			.addCommandExecutor("pie", new PieSample())
			.addCommandExecutor("pie2", new PieSample2())
			.addCommandExecutor("donut", new DonutSample())
			.addCommandExecutor("area", new AreaSample())
			.addCommandExecutor("line", new LineSample())
			.addCommandExecutor("radar", new RadarSample())
			.addCommandExecutor("f1", new FormSample1())
			.addCommandExecutor("f2", new FormSample2())
			.addCommandExecutor("f3", new FormSample3())
			.addCommandExecutor("f4", new FormSample4())
			.addCommandExecutor("f5", new FormSample5())
			.addCommandExecutor("f6", new FormSample6())
			.addCommandExecutor("board", new CrmInstallationBoard())

			.addCommandExecutor("figlet", cmd -> Shiny.figlet()
					.withText("Hello Vertigo")
					.build())
			.addCommandExecutor("textpath", cmd -> Shiny.textPath()
					.withPath("root/node/leaf")
					.withSeparator("/")
					.build())
			.addCommandExecutor("tp", cmd -> Shiny.textPath()
					.withPath("root/node/leaf")
					.withSeparator("/")
					.build())
			.addCommandExecutor("error", cmd -> Shiny.error()
					.withText("This is an error message")
					.build())
			.addCommandExecutor("title", cmd -> Shiny.title()
					.withText("This is a title")
					.withLevel(2)
					.build())
			.addCommandExecutor("paragraph", cmd -> Shiny.paragraph()
					.withText("This is a paragraph.")
					.build())
			.addCommandExecutor("toggle", cmd -> Shiny.toggle()
					.withLabel("Enable Feature")
					.withValue(true)
					.withType(ShinyToggleType.SWITCH)
					.withOnText("Active")
					.withOffText("Inactive")
					.withShowText(true)
					.build())
			.addCommandExecutor("gauge", cmd -> Shiny.gauge()
					.withTitle("Ventes par produit")
					.withValue(156)
					.withMaxValue(450)
					.build())
			.addCommandExecutor("sparkline", BansheeSamples::sparklineCommand)
			.addCommandExecutor("image", BansheeSamples::imageCommand)
			.addCommandExecutor("photo", BansheeSamples::imageCommand)
			.addCommandExecutor("card", BansheeSamples::cardCommand)
			.addCommandExecutor("list", BansheeSamples::listCommand)
			.addCommandExecutor("status", BansheeSamples::statusCommand)
			.addCommandExecutor("rating", BansheeSamples::ratingCommand)
			.addCommandExecutor("inputtext", BansheeSamples::inputTextCommand)
			.addCommandExecutor("json", BansheeSamples::jsonCommand)
			.addCommandExecutor("youtube", BansheeSamples::youtubeCommand)
			.addCommandExecutor("map", BansheeSamples::mapCommand)
			.addCommandExecutor("rss", BansheeSamples::rssCommand)
			.addCommandExecutor("rss franceinfo", BansheeSamples::rssFranceInfoCommand)
			.addCommandExecutor("rss france info", BansheeSamples::rssFranceInfoCommand2)
			.addCommandExecutor("rss bbc", BansheeSamples::bbcCommand)
			.addCommandExecutor("rss lemonde", BansheeSamples::lemondeCommand)
			.addCommandExecutor("slides", BansheeSamples::slidesCommand)
			.addCommandExecutor("table", BansheeSamples::tableCommand)
			.addCommandExecutor("timeline", BansheeSamples::timelineCommand)
			.addCommandExecutor("tl", BansheeSamples::timelineCommand)
			.addCommandExecutor("slider", BansheeSamples::sliderCommand)
			.addCommandExecutor("multiselection", BansheeSamples::multiselectionCommand)
			.addCommandExecutor("alert", BansheeSamples::alertCommand)
			.addCommandExecutor("alert-success", BansheeSamples::alertSuccessCommand)
			.addCommandExecutor("alert-warning", BansheeSamples::alertWarningCommand)
			.addCommandExecutor("alert-error", BansheeSamples::alertErrorCommand)
			.addCommandExecutor("chip", BansheeSamples::chipCommand)
			.addCommandExecutor("org", BansheeSamples::orgCommand)
			.addCommandExecutor("pdf", BansheeSamples::pdfCommand)
			.addCommandExecutor("tree", BansheeSamples::treeCommand)
			.addCommandExecutor("sankey", BansheeSamples::sankey)
			.addCommandExecutor("flow", BansheeSamples::flow)
			.addCommandExecutor("wait", cmd -> wait(Shiny.figlet()
					.withText("attente 2s")
					.build(), 2000))
			.addCommandExecutor("container", BansheeSamples::containerCommand)
			.addCommandExecutor("mindmap", BansheeSamples::mindmap)
			.addCommandExecutor("table3", BansheeSamples::table3)
			.addCommandExecutor("grid", BansheeSamples::grid)
			.addCommandExecutor("datagrid", BansheeSamples::datagridCommand)
			.addCommandExecutor("datepicker", BansheeSamples::datepickerCommand)
			.addCommandExecutor("fileupload", BansheeSamples::fileuploadCommand)
			.addCommandExecutor("codeeditor", BansheeSamples::codeeditorCommand)
			.addCommandExecutor("autocomplete", BansheeSamples::autocompleteCommand)
			.addCommandExecutor("rangeslider", BansheeSamples::rangesliderCommand)
			.addCommandExecutor("notification", BansheeSamples::notificationCommand)
			.addCommandExecutor("modal", BansheeSamples::modalCommand)
			.addCommandExecutor("page", BansheeSamples::page)
			.build();

	private static ShinyModel wait(final ShinyModel model, final int timeInMillis) {
		try {
			Thread.sleep(timeInMillis);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
		return model;
	}

	private static ShinyModel slidesCommand(final BansheeCommand cmd) {
		final String markdown = """
				             # Slide 1
				             Hello

				             ---
				             # Slide 2
				             World
				""";
		return Shiny.slide()
				.withMarkdown(markdown)
				.build();
	}

	private static ShinyModel modalCommand(final BansheeCommand cmd) {
		return Shiny.modal()
				.withTitle("My Modal")
				.withContent(
						Shiny.container()
								.addModel(
										Shiny.paragraph()
												.withText("This is the content of the modal.")
												.build())
								.build())
				.isPersistent()
				.build();
	}

	private static ShinyModel notificationCommand(final BansheeCommand cmd) {
		return Shiny.notification()
				.withType(ShinyNotificationType.SUCCESS)
				.withMessage("This is a success notification")
				.withTimeout(3000)
				.build();
	}

	private static ShinyModel rangesliderCommand(final BansheeCommand cmd) {
		return Shiny.rangeSlider()
				.withLabel("Price Range")
				.withMin(0)
				.withMax(1000)
				.withStep(10)
				.withValue(200, 800)
				.withColor("green")
				.withThumbLabel(true)
				.build();
	}

	private static ShinyModel autocompleteCommand(final BansheeCommand cmd) {
		return Shiny.autocomplete()
				.withLabel("Select a country")
				.addOption("France")
				.addOption("Germany")
				.addOption("Spain")
				.addOption("Italy")
				.withValue("France")
				.withPlaceholder("Start typing...")
				.build();
	}

	private static ShinyModel codeeditorCommand(final BansheeCommand cmd) {
		return Shiny.codeEditor()
				.withLanguage("javascript")
				.withContent("function hello() {\n  console.log('Hello, World!');\n}")
				.build();
	}

	private static ShinyModel fileuploadCommand(final BansheeCommand cmd) {
		return Shiny.fileUpload().withLabel("Upload a file")
				.isMultiple().withAccept("image/*").build();
	}

	private static ShinyModel datepickerCommand(final BansheeCommand cmd) {
		return Shiny.datePicker().withLabel("Select a date")
				.withValue("2025-11-13").isRequired().build();
	}

	private static ShinyModel datagridCommand(final BansheeCommand cmd) {
		return Shiny.dataGrid().withTitle("My DataGrid")
				.addColumn("ID", "id", true, true)
				.addColumn("Name", "name", true, false)
				.addColumn("Age", "age", true, false)
				.withData(List.of(
						Map.of("id", 1, "name", "John", "age", 30),
						Map.of("id", 2, "name", "Jane", "age", 25),
						Map.of("id", 3, "name", "Doe", "age", 40)))
				.build();
	}

	private static ShinyModel containerCommand(final BansheeCommand cmd) {
		return new ShinyContainerBuilder()
				.addModel(Shiny.sparkline()
						.withTitle("Ventes par produit")
						.withValues(156, 450, 300, 200, 100, 23)
						.build())
				.addModel(Shiny.list()
						.withTitle("planetes")
						.withType(ShinyListType.DASHED)
						.addItem("Uranus").addItem("Saturn").addItem("Venus").build())
				.addModel(Shiny.chip().withText("Beatles")
						.withColor("red")
						.withVariant(ShinyChipVariant.ELEVATED)
						.withIcon("mdi-vuetify")
						.withClosable(true).build())
				.addModel(Shiny.chip().withText("Kinks")
						.withColor("pink").withVariant(ShinyChipVariant.ELEVATED)
						.withIcon("mdi-vuetify")
						.build())
				.build();
	}

	private static ShinyModel treeCommand(final BansheeCommand cmd) {
		return Shiny.tree()
				.withLabel("my directory")
				.addTree("Files").addLeaf("src")
				.addTree("main").addLeaf("file.txt").up().up()
				.addTree("test").addLeaf("testFile.txt").root().build();
	}

	private static ShinyModel pdfCommand(final BansheeCommand cmd) {
		return Shiny.pdf()
				.withTitle("Arthur Rimbaud - Poèmes")
				.withPath("sample-report.pdf").build();
	}

	private static ShinyModel orgCommand(final BansheeCommand cmd) {
		return Shiny.organization()
				.addNode("1", null, "John Doe", "CEO", "https://randomuser.me/api/portraits/men/1.jpg")
				.addNode("2", "1", "Jane Smith", "CTO", "https://randomuser.me/api/portraits/women/2.jpg")
				.addNode("3", "1", "Mike Johnson", "CFO", "https://randomuser.me/api/portraits/men/3.jpg")
				.addNode("4", "2", "Emily Brown", "Lead Developer", "https://randomuser.me/api/portraits/women/4.jpg")
				.addNode("5", "2", "David Wilson", "DevOps Engineer", "https://randomuser.me/api/portraits/men/5.jpg")
				.addNode("6", "3", "Sarah Davis", "Accountant", "https://randomuser.me/api/portraits/women/6.jpg")
				.build();
	}

	private static ShinyModel chipCommand(final BansheeCommand cmd) {
		return Shiny.chip()
				.withText("Vuetify")
				.withColor("red")
				.withVariant(ShinyChipVariant.ELEVATED)
				.withIcon("mdi-vuetify")
				.withClosable(true)
				.build();
	}

	private static ShinyModel alertErrorCommand(final BansheeCommand cmd) {
		return Shiny.alert()
				.withAlertType(ShinyAlertType.ERROR)
				.withTitle("Error")
				.withContent("An error occurred while processing your request.")
				.build();
	}

	private static ShinyModel alertWarningCommand(final BansheeCommand cmd) {
		return Shiny.alert()
				.withAlertType(ShinyAlertType.WARNING)
				.withContent("This is a warning message, please be careful.")
				.build();
	}

	private static ShinyModel alertSuccessCommand(final BansheeCommand cmd) {
		return Shiny.alert()
				.withAlertType(ShinyAlertType.SUCCESS)
				.withTitle("Success")
				.withContent("The operation completed successfully!")
				.build();
	}

	private static ShinyModel alertCommand(final BansheeCommand cmd) {
		return Shiny.alert()
				.withAlertType(ShinyAlertType.INFO)
				.withTitle("Information")
				.withContent("This is an informational message.")
				.withClosable(true)
				.build();
	}

	private static ShinyModel multiselectionCommand(final BansheeCommand cmd) {
		return Shiny.multiSelection()
				.withTitle("Select your favorite fruits")
				.addOption("Apple")
				.addOption("Banana")
				.addOption("Orange")
				.addOption("Grape")
				.build();
	}

	private static ShinyModel sliderCommand(final BansheeCommand cmd) {
		return Shiny.slider()
				.withLabel("Volume")
				.withMin(0)
				.withMax(100)
				.withStep(1)
				.withValue(45)
				.withColor("orange")
				.withThumbLabel(true)
				.build();
	}

	private static ShinyModel timelineCommand(final BansheeCommand cmd) {
		return Shiny.timeline()
				.withTitle("Project Timeline")
				.addItem("Step 1: Conception", "Defining project goals and scope.", "blue", "mdi-lightbulb-on-outline")
				.addItem("Step 2: Development", "Building the core features.", "green", "mdi-code-braces")
				.addItem("Step 3: Testing", "Ensuring quality and stability.", "orange", "mdi-flask-empty-outline")
				.addItem("Step 4: Deployment", "Releasing to production.", "purple", "mdi-rocket-launch-outline")
				.build();
	}

	private static ShinyModel tableCommand(final BansheeCommand cmd) {
		return Shiny.table()
				.withTitle("carnet d'adresses")
				.withNoDataFound("no files found")
				.withHeader("Prénom", "Nom")
				.addRow("Arthur", "Penn")
				.addRow("Marilyn", "Pinson")
				.build();
	}

	private static ShinyModel lemondeCommand(final BansheeCommand cmd) {
		return Shiny.rss()
				.withFeed("https://www.lemonde.fr/rss/une.xml")
				.build();
	}

	private static ShinyModel bbcCommand(final BansheeCommand cmd) {
		return Shiny.rss()
				.withFeed("https://feeds.bbci.co.uk/news/world/rss.xml")
				.build();
	}

	private static ShinyModel rssFranceInfoCommand2(final BansheeCommand cmd) {
		return Shiny.rss()
				.withFeed("https://www.francetvinfo.fr/titres.rss")
				.build();
	}

	private static ShinyModel rssFranceInfoCommand(final BansheeCommand cmd) {
		return Shiny.rss()
				.withFeed("https://www.francetvinfo.fr/titres.rss")
				.build();
	}

	private static ShinyModel rssCommand(final BansheeCommand cmd) {
		return Shiny.rss()
				.withFeed("https://www.francetvinfo.fr/titres.rss")
				.build();
	}

	private static ShinyModel mapCommand(final BansheeCommand cmd) {
		return Shiny.geoMap()
				.withTitle("Tour Eiffel & Saint Germain")
				.addGeoPoint(ShinyGeoPoint.of(48.8584, 2.2945, "Tour Eiffel"))
				.addGeoPoint(ShinyGeoPoint.of(48.901022, 2.100765, "Saint Germain en Laye"))
				.build();
	}

	private static ShinyModel youtubeCommand(final BansheeCommand cmd) {
		return Shiny.youtube()
				.withTitle("Rick Astley - Never Gonna Give You Up")
				.withVideoId("dQw4w9WgXcQ")
				.build();
	}

	private static ShinyModel jsonCommand(final BansheeCommand cmd) {
		return Shiny.json()
				.withJson(
						"""
								{
								"title": "The Shining",
								"director": "Stanley Kubrick",
								"release_year": 1980,
								"genre": ["Horror", "Thriller"],
								"duration": "2h 26m",
								"cast": ["Jack Nicholson", "Shelley Duvall", "Danny Lloyd"],
								"synopsis": "A family heads to an isolated hotel for the winter."
								}
								""")
				.withTitle("Fiche de Shinning")
				.build();
	}

	private static ShinyModel inputTextCommand(final BansheeCommand cmd) {
		return Shiny.inputText()
				.withLabel("Your Name")
				.withDefaultValue("John Doe")
				.build();
	}

	private static ShinyModel ratingCommand(final BansheeCommand cmd) {
		return Shiny.rating()
				.withLabel("User satisfaction")
				.withValue(3.5)
				.withScale(ShinyRatingScale.SCALE_5)
				.withAllowHalfRating(true)
				.build();
	}

	private static ShinyModel statusCommand(final BansheeCommand cmd) {
		return Shiny.status()
				.withTitle("Component Status")
				.addType(ShinyStatusType.SUCCESS)
				.addType(ShinyStatusType.ERROR)
				.addType(ShinyStatusType.WARNING)
				.build();
	}

	private static ShinyModel listCommand(final BansheeCommand cmd) {
		return Shiny.list().withTitle("planetes")
				.withType(ShinyListType.UNORDERED).addItem("Uranus").addList(Shiny.list().withTitle("Mars").withType(ShinyListType.UNORDERED).addItem("Bleue").addItem("Rouge").addItem("Verte").build()).addItem("Saturn").addItem("Venus").build();
	}

	private static ShinyModel cardCommand(final BansheeCommand cmd) {
		return Shiny.card()
				.withTitle("Mon Titre de Carte")
				.withSubtitle("Un sous-titre pour le contexte").withDescription("Ceci est le contenu principal de ma carte. Il peut être plus long et contenir des informations détaillées sur le sujet de la carte.").withImageUrl("https://picsum.photos/id/237/200/300").withImageAlt("Image aléatoire de Picsum").withLink("https://www.vertigo.io").withIcon("star").withBadge("Nouveau", "blue").withFormat(ShinyCardFormat.M).build();
	}

	private static ShinyModel imageCommand(final BansheeCommand cmd) {
		return Shiny.image()
				.withTitle("Random image from picsum")
				.withUrl("https://picsum.photos/800/600")
				.withAlt("Random image from picsum")
				.build();
	}

	private static ShinyModel sparklineCommand(final BansheeCommand cmd) {
		return Shiny.sparkline()
				.withTitle("S.p.a.r.k.l.i.n.e")
				.withValues(156, 450, 300, 200, 100, 23)
				.build();
	}

	//BansheeCommandExcecutor 
	private static ShinyModel table2(BansheeCommand cmd) {
		final ShinyTableBuilder tableBuilder = Shiny.table()
				.withTitle("alphabet")
				.withHeader("Lettre de A à Z");
		final String page = cmd.state() == null
				? "1"
				: cmd.state().getValue("page")
						.orElseGet(() -> "1");

		switch (page) {
			case "1":
				tableBuilder.addRow("A").addRow("B").addRow("C").addRow("D").addRow("E");
				break;
			case "2":
				tableBuilder.addRow("F").addRow("G").addRow("H").addRow("I").addRow("J");
				break;
			case "3":
				tableBuilder.addRow("K").addRow("L").addRow("M").addRow("N").addRow("O");
				break;
			case "4":
				tableBuilder.addRow("P").addRow("Q").addRow("R").addRow("S").addRow("T");
				break;
			case "5":
				tableBuilder.addRow("U").addRow("V").addRow("W").addRow("X").addRow("Y");
				break;
			case "6":
				tableBuilder.addRow("Z");
				break;
			default:
				tableBuilder.withNoDataFound("No data Found");
				break;
		}
		final UUID id = cmd.id() != null
				? cmd.id()
				: UUID.randomUUID();
		return tableBuilder
				.withId(id)
				.withState(new ShinyTableStateBuilder()
						.withSortColumn(0)
						.withPageCount(6)
						.withPage(Integer.valueOf(page).intValue())
						.build())
				.build();
	}

	private static ShinyModel mindmap(BansheeCommand cmd) {
		return Shiny.mindMap()
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
														new ShinyMindMapNodeBuilder("impressionnisme", "Impressionnisme").build(), new ShinyMindMapNodeBuilder("cubisme", "Cubisme").build())
												.build(),
										new ShinyMindMapNodeBuilder("contemporain", "Art Contemporain").withDirection("right").addAllChildren(new ShinyMindMapNodeBuilder("pop_art", "Pop Art").build(),
												new ShinyMindMapNodeBuilder("minimalisme", "Minimalisme").build())
												.build())
								.build())
				.build();
	}

	private static ShinyModel page(BansheeCommand cmd) {
		return Shiny.page()
				.withTitle("My Sample Page")
				.withLayout(
						Shiny.grid()
								.addBlock(
										Shiny.card()
												.withTitle("Card on Page")
												.withDescription("This card is part of the page layout.")
												.build())
								.addBlock(
										Shiny.container()
												.addModel(
														Shiny.alert()
																.withAlertType(ShinyAlertType.INFO)
																.withContent("This is an alert on the page.")
																.build())
												.build())
								.build())
				.build();
	}

	private static ShinyModel table3(BansheeCommand cmd) {
		return Shiny.table()
				.withTitle("Rich Content Table")
				.withHeader("Name", "Status", "Progress", "Rating", "Action", "Avatar", "Icon", "Badge")
				.addRow(new ShinyStringCell(UUID.randomUUID(), "Alice"),
						new ShinyChipCell(UUID.randomUUID(), "Active", "green", ShinyChipVariant.FLAT, false, null),
						new ShinyProgressBarCell(UUID.randomUUID(), 75, 100, "blue"),
						new ShinyRatingCell(UUID.randomUUID(), 4.5, ShinyRatingScale.SCALE_5, true),
						new ShinyButtonCell(UUID.randomUUID(), "View", "primary", "viewAlice"),
						new ShinyAvatarCell(UUID.randomUUID(), "https://randomuser.me/api/portraits/women/1.jpg", "Alice", "36px"),
						new ShinyIconCell(UUID.randomUUID(), "mdi-check-circle", "green", "24px"),
						new ShinyBadgeCell(UUID.randomUUID(), "New", "red"))
				.addRow(
						new ShinyStringCell(UUID.randomUUID(), "Bob"),
						new ShinyChipCell(UUID.randomUUID(), "Inactive", "red", ShinyChipVariant.OUTLINED, false, null),
						new ShinyProgressBarCell(UUID.randomUUID(), 25, 100, "red"),
						new ShinyRatingCell(UUID.randomUUID(), 2.0, ShinyRatingScale.SCALE_5, false),
						new ShinyButtonCell(UUID.randomUUID(), "Edit", "secondary", "editBob"),
						new ShinyAvatarCell(UUID.randomUUID(), "https://randomuser.me/api/portraits/men/2.jpg", "Bob", "36px"),
						new ShinyIconCell(UUID.randomUUID(), "mdi-alert-circle", "orange", "24px"),
						new ShinyBadgeCell(UUID.randomUUID(), "Urgent", "orange"))
				.addRow(
						new ShinyStringCell(UUID.randomUUID(), "Charlie"),
						new ShinyChipCell(UUID.randomUUID(), "Pending", "orange", ShinyChipVariant.ELEVATED, false, null),
						new ShinyProgressBarCell(UUID.randomUUID(), 50, 100, "orange"),
						new ShinyRatingCell(UUID.randomUUID(), 3.0, ShinyRatingScale.SCALE_5, true),
						new ShinyButtonCell(UUID.randomUUID(), "Delete", "error", "deleteCharlie"),
						new ShinyAvatarCell(UUID.randomUUID(), "https://randomuser.me/api/portraits/men/3.jpg", "Charlie", "36px"),
						new ShinyIconCell(UUID.randomUUID(), "mdi-information", "blue", "24px"),
						new ShinyBadgeCell(UUID.randomUUID(), "Info", "blue"))
				.build();
	}

	private static ShinyModel sankey(BansheeCommand cmd) {
		return Shiny.sankey()
				.withTitle("Flux d\'énergie")
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
	}

	private static ShinyModel flow(BansheeCommand cmd) {
		return new ShinyFlowBuilder()
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
	}

	private static ShinyModel grid(BansheeCommand cmd) {
		return Shiny.grid()
				.withColumns(2)
				.addBlock(
						Shiny.card()
								.withTitle("Card 1")
								.withDescription("This is the first card in the grid.")
								.build())
				.addBlock(
						Shiny.card()
								.withTitle("Card 2")
								.withDescription("This is the second card in the grid.")
								.build())
				.addBlock(
						Shiny.container()
								.addModel(
										Shiny.paragraph()
												.withText("This is a paragraph in the grid.")
												.build())
								.build())
				.addBlock(
						Shiny.container()
								.addModel(
										Shiny.alert()
												.withAlertType(ShinyAlertType.WARNING)
												.withContent("This is an alert in the grid.")
												.build())
								.build())
				.build();
	}

}
