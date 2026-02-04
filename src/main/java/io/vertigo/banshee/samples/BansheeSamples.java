package io.vertigo.banshee.samples;

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
import io.vertigo.shiny.models.data.list.ShinyListType;
import io.vertigo.shiny.models.feedback.alert.ShinyAlertType;
import io.vertigo.shiny.models.text.chip.ShinyChipVariant;

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

			//--INPUT
			.addCommandExecutor("autocomplete", InputSamples::autocomplete)
			.addCommandExecutor("codeeditor", InputSamples::codeeditor)
			.addCommandExecutor("datepicker", InputSamples::datepicker)
			.addCommandExecutor("fileupload", InputSamples::fileupload)
			.addCommandExecutor("f1", InputSamples::formSample1)
			.addCommandExecutor("f2", InputSamples::formSample2)
			.addCommandExecutor("f3", InputSamples::formSample3)
			.addCommandExecutor("f4", InputSamples::formSample4)
			.addCommandExecutor("f5", InputSamples::formSample5)
			.addCommandExecutor("f6", InputSamples::formSample6)
			.addCommandExecutor("multiselection", InputSamples::multiselection)
			.addCommandExecutor("rangeslider", InputSamples::rangeslider)
			.addCommandExecutor("slider", InputSamples::slider)
			.addCommandExecutor("inputtext", InputSamples::inputText)
			.addCommandExecutor("toggle", InputSamples::toggle)

			//--DATA
			.addCommandExecutor("board", DataBoardSamples::crm)
			.addCommandExecutor("card", DataSamples::card)
			.addCommandExecutor("datagrid", DataSamples::datagrid)
			.addCommandExecutor("json", DataSamples::json)
			.addCommandExecutor("list", DataSamples::list)
			.addCommandExecutor("table", DataSamples::table)
			.addCommandExecutor("table2", DataSamples::table2)
			.addCommandExecutor("table3", DataSamples::table3)
			.addCommandExecutor("tree", DataSamples::tree)

			//--FEEDBACK
			.addCommandExecutor("alert-info", FeedbackSamples::alertInfo)
			.addCommandExecutor("alert-success", FeedbackSamples::alertSuccess)
			.addCommandExecutor("alert-warning", FeedbackSamples::alertWarning)
			.addCommandExecutor("alert-error", FeedbackSamples::alertError)
			.addCommandExecutor("modal", FeedbackSamples::modal)
			.addCommandExecutor("notification", FeedbackSamples::notification)

			//------------------------
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

			.addCommandExecutor("error", cmd -> Shiny.error()
					.withText("This is an error message")
					.build())

			.addCommandExecutor("wait", BansheeSamples::waiting)
			.addCommandExecutor("container", BansheeSamples::containerCommand)
			.addCommandExecutor("grid", BansheeSamples::grid)

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

	//BansheeCommandExcecutor 

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
