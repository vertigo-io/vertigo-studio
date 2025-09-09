package io.vertigo.shiny;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import io.vertigo.shiny.components.data.calendar.ShinyCalendar;
import io.vertigo.shiny.components.data.json.ShinyJson;
import io.vertigo.shiny.components.data.list.ShinyList;
import io.vertigo.shiny.components.data.table.ShinyTable;
import io.vertigo.shiny.components.data.tree.ShinyTree;
import io.vertigo.shiny.components.dataviz.barchart.ShinyBarChart;
import io.vertigo.shiny.components.dataviz.gauge.ShinyGauge;
import io.vertigo.shiny.components.dataviz.rating.ShinyRating;
import io.vertigo.shiny.components.dataviz.sparkline.ShinySparkline;
import io.vertigo.shiny.components.dataviz.status.ShinyStatus;
import io.vertigo.shiny.components.input.multiselection.ShinyMultiSelection;
import io.vertigo.shiny.components.input.text.ShinyInputText;
import io.vertigo.shiny.components.live.progressbar.ShinyProgressBar;
import io.vertigo.shiny.components.live.spinner.ShinySpinner;
import io.vertigo.shiny.components.text.figlet.ShinyFiglet;
import io.vertigo.shiny.components.text.markdown.ShinyMarkDown;
import io.vertigo.shiny.components.text.paragraph.ShinyParagraph;
import io.vertigo.shiny.components.text.textpath.ShinyTextPath;
import io.vertigo.shiny.components.text.title.ShinyTitle;
import io.vertigo.shiny.components.text.toggle.ShinyToggle;
import io.vertigo.shiny.mermaid.ShinyMermaidServer;

public final class Shiny {
	private PrintWriter writer = new PrintWriter(System.out, true, StandardCharsets.UTF_8);

	private static final Shiny INSTANCE = new Shiny();

	private final ShinyTheme theme = new ShinyTheme();

	public static void printWriter(PrintWriter printWriter) {
		INSTANCE.writer = printWriter;
	}

	public static ShinyWriter writer() {
		return new ShinyWriter(INSTANCE.writer);
	}

	private Shiny() {
		writer = new PrintWriter(System.out, true, StandardCharsets.UTF_8);
	}

	public static ShinyTable table() {
		return new ShinyTable();
	}

	public static ShinyBarChart barChart() {
		return new ShinyBarChart();
	}

	public static ShinyProgressBar progressBar() {
		return new ShinyProgressBar();
	}

	public static ShinySpinner spinner() {
		return new ShinySpinner();
	}

	public static ShinyTree tree(final String label) {
		return new ShinyTree(label);
	}

	public static ShinyGauge gauge() {
		return new ShinyGauge();
	}

	public static ShinySparkline sparkline() {
		return new ShinySparkline();
	}

	public static ShinyStatus status() {
		return new ShinyStatus();
	}

	public static ShinyFiglet figlet() {
		return new ShinyFiglet();
	}

	public static ShinyCalendar calendar() {
		return new ShinyCalendar();
	}

	public static ShinyTextPath textPath() {
		return new ShinyTextPath();
	}

	public static ShinyJson json() {
		return new ShinyJson();
	}

	public static ShinyList list() {
		return new ShinyList();
	}

	public static ShinyTitle title() {
		return new ShinyTitle();
	}

	public static ShinyMarkDown markdown() {
		return new ShinyMarkDown(INSTANCE);
	}

	public static ShinyParagraph paragraph() {
		return new ShinyParagraph();
	}

	public static ShinyToggle toggle() {
		return new ShinyToggle();
	}

	public static ShinyRating rating() {
		return new ShinyRating();
	}

	public static ShinyMultiSelection multiSelection() {
		return new ShinyMultiSelection();
	}

	public static ShinyInputText inputText() {
		return new ShinyInputText();
	}

	public static ShinyTheme theme() {
		return INSTANCE.theme;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	//-----
	public static ShinyMermaidServer mermaid() {
		return new ShinyMermaidServer(INSTANCE, 5656);
	}
}
