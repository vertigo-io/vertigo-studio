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
import io.vertigo.shiny.components.core.container.ShinyContainer;
import io.vertigo.shiny.components.core.error.ShinyError;
import io.vertigo.shiny.components.data.json.ShinyJson;
import io.vertigo.shiny.components.data.list.ShinyList;
import io.vertigo.shiny.components.data.list.ShinyListType;
import io.vertigo.shiny.components.data.table.ShinyTable;
import io.vertigo.shiny.components.data.tree.ShinyTree;
import io.vertigo.shiny.components.dataviz.area.ShinyAreaChart;
import io.vertigo.shiny.components.dataviz.barchart.ShinyBarChart;
import io.vertigo.shiny.components.dataviz.donut.ShinyDonutChart;
import io.vertigo.shiny.components.dataviz.gauge.ShinyGauge;
import io.vertigo.shiny.components.dataviz.pie.ShinyPieChart;
import io.vertigo.shiny.components.dataviz.radar.ShinyRadarChart;
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
			switch (message) {
				// ... (rest of the switch cases)
				case "xpie":
					var pieChart = Shiny.pieChart()
							.withTitle("Répartition des ventes")
							.withLabels("Téléphones", "Ordinateurs", "Livres")
							.addSerie("Ventes", 40.0, 20.0, 15.0)
							.build();
					sendMessage(webSocket, pieChart);
					break;
				case "xdonut":
					var donutChart = Shiny.donutChart()
							.withTitle("Répartition des ventes")
							.withLabels("Téléphones", "Ordinateurs", "Livres")
							.addSerie("Ventes", 40.0, 20.0, 15.0)
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
				// ... (rest of the switch cases)
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getType(ShinyComponent component) {
		final String type = switch (component) {
			case ShinyTable c -> "table";
			case ShinyTextPath c -> "textPath";
			case ShinyParagraph c -> "paragraph";
			case ShinyTitle c -> "title";
			case ShinyError c -> "error";
			case ShinyList c -> "list";
			case ShinyJson c -> "json";
			case ShinyBarChart c -> "barChart";
			case ShinyAreaChart c -> "areaChart";
			case ShinyDonutChart c -> "donutChart";
			case ShinyPieChart c -> "pieChart";
			case ShinyRadarChart c -> "radarChart";
			case ShinyGauge c -> "gauge";
			case ShinySparkline c -> "sparkLine";
			case ShinyStatus c -> "status";
			case ShinyRating c -> "rating";
			case ShinyContainer c -> "container";
			case ShinyFiglet c -> "figlet";
			case ShinyForm c -> "form";
			case ShinyTree c -> "tree";
			default -> throw new IllegalArgumentException("Unknown component type: " + component.getClass());
		};
		return type;
	}

	// ... (rest of the class)
}