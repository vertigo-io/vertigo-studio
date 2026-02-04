package io.vertigo.banshee.samples;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.vertigo.banshee.com.BansheeCommandHandler;
import io.vertigo.banshee.com.BansheeCommandHandlerBuilder;
import io.vertigo.banshee.com.BansheeCommandLine;
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
import io.vertigo.shiny.models.feedback.alert.ShinyAlertType;
import io.vertigo.shiny.models.feedback.notification.ShinyNotificationType;
import io.vertigo.shiny.models.input.toggle.ShinyToggleType;
import io.vertigo.shiny.models.text.chip.ShinyChipVariant;
import io.vertigo.shiny.models.text.rating.ShinyRatingScale;

public final class BansheeSamples {
	public static final BansheeCommandHandler commandHandler = new BansheeCommandHandlerBuilder()
			.addCommandExecutor("llm", new LLMCommandExecutor())

			//--MEDIA	
			/* args attendus : bbc, lemonde, france info */
			.addCommandExecutor("rss", MediaSamples::rss)
			.addCommandExecutor("image", MediaSamples::image)
			.addCommandExecutor("youtube", MediaSamples::youtube)
			.addCommandExecutor("slides", MediaSamples::slides)
			.addCommandExecutor("pdf", MediaSamples::pdf)

			//--DATAVIZ
			.addCommandExecutor("bar", DatavizSamples::barSample)
			.addCommandExecutor("pie", DatavizSamples::pieSample)
			.addCommandExecutor("pie2", DatavizSamples::pieSample2)
			.addCommandExecutor("donut", DatavizSamples::donutSample)
			.addCommandExecutor("area", DatavizSamples::areaSample)
			.addCommandExecutor("line", DatavizSamples::lineSample)
			.addCommandExecutor("radar", DatavizSamples::radarSample)
			.addCommandExecutor("timeline", DatavizSamples::timeline)
			.addCommandExecutor("sankey", DatavizSamples::sankey)
			.addCommandExecutor("org", DatavizSamples::organization)
			.addCommandExecutor("flow", DatavizSamples::flow)
			.addCommandExecutor("gauge", DatavizSamples::gauge)
			.addCommandExecutor("map", DatavizSamples::map)
			.addCommandExecutor("mindmap", DatavizSamples::mindmap)

			//--TEXT
			.addCommandExecutor("figlet", TextSamples::figlet)
			.addCommandExecutor("title", TextSamples::title)
			.addCommandExecutor("paragraph", TextSamples::paragraph)
			.addCommandExecutor("textpath", TextSamples::textpath)
			.addCommandExecutor("textpath2", TextSamples::textpath2)
			.addCommandExecutor("sparkline", TextSamples::sparkline)
			.addCommandExecutor("status", TextSamples::status)
			.addCommandExecutor("rating", TextSamples::rating)
			.addCommandExecutor("chip", TextSamples::chip)
			.addCommandExecutor("table2", BansheeSamples::table2)

			.addCommandExecutor("ls", cmd -> new FileLsCommand().build())
			.addCommandExecutor("pwd", cmd -> new FilePwdCommand().build())
			.addCommandExecutor("env", new BansheeCommandHandlerBuilder()
					.addCommandExecutor("list", cmd -> new EnvListCommand().build())
					.build())
			.addCommandExecutor("uptime", cmd -> new UptimeCommand().build())
			.addCommandExecutor("ip", cmd -> new IpCommand().build())

			.addCommandExecutor("db", new BansheeCommandHandlerBuilder()
					.addCommandExecutor("connect", cmd -> new DbConnectCommand().build())
					.addCommandExecutor("disconnect", cmd -> new DbDisconnectCommand().build())
					.addCommandExecutor("load", cmd -> new DbLoadCommand().build())
					.addCommandExecutor("read", cmd -> new DbReadCommand().build())
					.addCommandExecutor("show", new BansheeCommandHandlerBuilder()
							.addCommandExecutor("tables", cmd -> new DbShowTablesCommand().build())
							.addCommandExecutor("model", cmd -> new DbShowModelCommand().build())
							.build())
					.build())

			.addCommandExecutor("java", new BansheeCommandHandlerBuilder()
					.addCommandExecutor("load", cmd -> new JavaLoadCommand().build())
					.addCommandExecutor("show", new BansheeCommandHandlerBuilder()
							.addCommandExecutor("model", cmd -> new JavaShowModelCommand().build())
							.build())
					.build())

			.addCommandExecutor("board", BoardSamples::crm)

			.addCommandExecutor("f1", FormSamples::formSample1)
			.addCommandExecutor("f2", FormSamples::formSample2)
			.addCommandExecutor("f3", FormSamples::formSample3)
			.addCommandExecutor("f4", FormSamples::formSample4)
			.addCommandExecutor("f5", FormSamples::formSample5)
			.addCommandExecutor("f6", FormSamples::formSample6)

			.addCommandExecutor("error", cmd -> Shiny.error()
					.withText("This is an error message")
					.build())
			.addCommandExecutor("toggle", BansheeSamples::toggle)
			.addCommandExecutor("card", BansheeSamples::cardCommand)
			.addCommandExecutor("list", BansheeSamples::listCommand)
			.addCommandExecutor("inputtext", BansheeSamples::inputTextCommand)
			.addCommandExecutor("json", BansheeSamples::jsonCommand)
			.addCommandExecutor("table", BansheeSamples::tableCommand)
			.addCommandExecutor("slider", BansheeSamples::sliderCommand)
			.addCommandExecutor("multiselection", BansheeSamples::multiselectionCommand)
			.addCommandExecutor("alert", BansheeSamples::alertCommand)
			.addCommandExecutor("alert-success", BansheeSamples::alertSuccessCommand)
			.addCommandExecutor("alert-warning", BansheeSamples::alertWarningCommand)
			.addCommandExecutor("alert-error", BansheeSamples::alertErrorCommand)
			.addCommandExecutor("tree", BansheeSamples::treeCommand)
			.addCommandExecutor("wait", BansheeSamples::waiting)
			.addCommandExecutor("container", BansheeSamples::containerCommand)
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

	private static ShinyModel waiting() {
		try {
			Thread.sleep(2000);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
		return Shiny.figlet()
				.withText("attente 2s")
				.build();
	}

	private static ShinyModel toggle() {
		return Shiny.toggle()
				.withLabel("Enable Feature")
				.withValue(true)
				.withType(ShinyToggleType.SWITCH)
				.withOnText("Active")
				.withOffText("Inactive")
				.withShowText(true)
				.build();
	}

	private static ShinyModel modalCommand(final BansheeCommandLine cmd) {
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

	private static ShinyModel notificationCommand(final BansheeCommandLine cmd) {
		return Shiny.notification()
				.withType(ShinyNotificationType.SUCCESS)
				.withMessage("This is a success notification")
				.withTimeout(3000)
				.build();
	}

	private static ShinyModel rangesliderCommand(final BansheeCommandLine cmd) {
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

	private static ShinyModel autocompleteCommand(final BansheeCommandLine cmd) {
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

	private static ShinyModel codeeditorCommand(final BansheeCommandLine cmd) {
		return Shiny.codeEditor()
				.withLanguage("javascript")
				.withContent("function hello() {\n  console.log('Hello, World!');\n}")
				.build();
	}

	private static ShinyModel fileuploadCommand(final BansheeCommandLine cmd) {
		return Shiny.fileUpload().withLabel("Upload a file")
				.isMultiple().withAccept("image/*").build();
	}

	private static ShinyModel datepickerCommand(final BansheeCommandLine cmd) {
		return Shiny.datePicker().withLabel("Select a date")
				.withValue("2025-11-13").isRequired().build();
	}

	private static ShinyModel datagridCommand(final BansheeCommandLine cmd) {
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

	private static ShinyModel containerCommand(final BansheeCommandLine cmd) {
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

	private static ShinyModel treeCommand(final BansheeCommandLine cmd) {
		return Shiny.tree()
				.withLabel("my directory")
				.addTree("Files").addLeaf("src")
				.addTree("main").addLeaf("file.txt").up().up()
				.addTree("test").addLeaf("testFile.txt").root().build();
	}

	private static ShinyModel alertErrorCommand(final BansheeCommandLine cmd) {
		return Shiny.alert()
				.withAlertType(ShinyAlertType.ERROR)
				.withTitle("Error")
				.withContent("An error occurred while processing your request.")
				.build();
	}

	private static ShinyModel alertWarningCommand(final BansheeCommandLine cmd) {
		return Shiny.alert()
				.withAlertType(ShinyAlertType.WARNING)
				.withContent("This is a warning message, please be careful.")
				.build();
	}

	private static ShinyModel alertSuccessCommand(final BansheeCommandLine cmd) {
		return Shiny.alert()
				.withAlertType(ShinyAlertType.SUCCESS)
				.withTitle("Success")
				.withContent("The operation completed successfully!")
				.build();
	}

	private static ShinyModel alertCommand(final BansheeCommandLine cmd) {
		return Shiny.alert()
				.withAlertType(ShinyAlertType.INFO)
				.withTitle("Information")
				.withContent("This is an informational message.")
				.withClosable(true)
				.build();
	}

	private static ShinyModel multiselectionCommand(final BansheeCommandLine cmd) {
		return Shiny.multiSelection()
				.withTitle("Select your favorite fruits")
				.addOption("Apple")
				.addOption("Banana")
				.addOption("Orange")
				.addOption("Grape")
				.build();
	}

	private static ShinyModel sliderCommand(final BansheeCommandLine cmd) {
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

	private static ShinyModel tableCommand(final BansheeCommandLine cmd) {
		return Shiny.table()
				.withTitle("carnet d'adresses")
				.withNoDataFound("no files found")
				.withHeader("Prénom", "Nom")
				.addRow("Arthur", "Penn")
				.addRow("Marilyn", "Pinson")
				.build();
	}

	private static ShinyModel jsonCommand(final BansheeCommandLine cmd) {
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

	private static ShinyModel inputTextCommand(final BansheeCommandLine cmd) {
		return Shiny.inputText()
				.withLabel("Your Name")
				.withDefaultValue("John Doe")
				.build();
	}

	private static ShinyModel listCommand(final BansheeCommandLine cmd) {
		return Shiny.list().withTitle("planetes")
				.withType(ShinyListType.UNORDERED).addItem("Uranus").addList(Shiny.list().withTitle("Mars").withType(ShinyListType.UNORDERED).addItem("Bleue").addItem("Rouge").addItem("Verte").build()).addItem("Saturn").addItem("Venus").build();
	}

	private static ShinyModel cardCommand(final BansheeCommandLine cmd) {
		return Shiny.card()
				.withTitle("Mon Titre de Carte")
				.withSubtitle("Un sous-titre pour le contexte").withDescription("Ceci est le contenu principal de ma carte. Il peut être plus long et contenir des informations détaillées sur le sujet de la carte.").withImageUrl("https://picsum.photos/id/237/200/300").withImageAlt("Image aléatoire de Picsum").withLink("https://www.vertigo.io").withIcon("star").withBadge("Nouveau", "blue").withFormat(ShinyCardFormat.M).build();
	}

	//BansheeCommandExcecutor 
	private static ShinyModel table2(BansheeCommandLine cmd) {
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

	private static ShinyModel page(BansheeCommandLine cmd) {
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

	private static ShinyModel table3(BansheeCommandLine cmd) {
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

	private static ShinyModel grid(BansheeCommandLine cmd) {
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
