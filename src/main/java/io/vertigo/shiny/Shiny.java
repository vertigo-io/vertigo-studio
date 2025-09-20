package io.vertigo.shiny;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import io.vertigo.shiny.components.data.calendar.ShinyCalendar;
import io.vertigo.shiny.components.data.calendar.ShinyCalendarBuilder;
import io.vertigo.shiny.components.data.json.ShinyJson;
import io.vertigo.shiny.components.data.json.ShinyJsonBuilder;
import io.vertigo.shiny.components.data.list.ShinyList;
import io.vertigo.shiny.components.data.list.ShinyListBuilder;
import io.vertigo.shiny.components.data.table.ShinyTable;
import io.vertigo.shiny.components.data.table.ShinyTableBuilder;
import io.vertigo.shiny.components.data.tree.ShinyTree;
import io.vertigo.shiny.components.data.tree.ShinyTreeBuilder;
import io.vertigo.shiny.components.dataviz.barchart.ShinyBarChart;
import io.vertigo.shiny.components.dataviz.barchart.ShinyBarChartBuilder;
import io.vertigo.shiny.components.dataviz.gauge.ShinyGauge;
import io.vertigo.shiny.components.dataviz.gauge.ShinyGaugeBuilder;
import io.vertigo.shiny.components.dataviz.rating.ShinyRating;
import io.vertigo.shiny.components.dataviz.rating.ShinyRatingBuilder;
import io.vertigo.shiny.components.dataviz.sparkline.ShinySparkline;
import io.vertigo.shiny.components.dataviz.sparkline.ShinySparklineBuilder;
import io.vertigo.shiny.components.dataviz.status.ShinyStatus;
import io.vertigo.shiny.components.dataviz.status.ShinyStatusBuilder;
import io.vertigo.shiny.components.error.ShinyError;
import io.vertigo.shiny.components.error.ShinyErrorBuilder;
import io.vertigo.shiny.components.input.multiselection.ShinyMultiSelection;
import io.vertigo.shiny.components.input.multiselection.ShinyMultiSelectionBuilder;
import io.vertigo.shiny.components.input.text.ShinyInputText;
import io.vertigo.shiny.components.input.text.ShinyInputTextBuilder;
import io.vertigo.shiny.components.live.progressbar.ShinyProgressBar;
import io.vertigo.shiny.components.live.progressbar.ShinyProgressBarBuilder;
import io.vertigo.shiny.components.live.spinner.ShinySpinner;
import io.vertigo.shiny.components.live.spinner.ShinySpinnerBuilder;
import io.vertigo.shiny.components.text.figlet.ShinyFiglet;
import io.vertigo.shiny.components.text.figlet.ShinyFigletBuilder;
import io.vertigo.shiny.components.text.paragraph.ShinyParagraph;
import io.vertigo.shiny.components.text.paragraph.ShinyParagraphBuilder;
import io.vertigo.shiny.components.text.textpath.ShinyTextPath;
import io.vertigo.shiny.components.text.textpath.ShinyTextPathBuilder;
import io.vertigo.shiny.components.text.title.ShinyTitle;
import io.vertigo.shiny.components.text.title.ShinyTitleBuilder;
import io.vertigo.shiny.components.text.toggle.ShinyToggle;
import io.vertigo.shiny.components.text.toggle.ShinyToggleBuilder;
import io.vertigo.shiny.mermaid.ShinyMermaidServer;

public final class Shiny {
	private PrintWriter writer = new PrintWriter(System.out, true, StandardCharsets.UTF_8);

	private static final Shiny INSTANCE = new Shiny();

	private final ShinyTheme theme = new ShinyTheme();

	public static void withWriter(PrintWriter printWriter) {
		INSTANCE.writer = printWriter;
	}

	public static ShinyWriter writer() {
		return new ShinyWriter(INSTANCE.writer);
	}

	private Shiny() {
		writer = new PrintWriter(System.out, true, StandardCharsets.UTF_8);
	}

	public static ShinyErrorBuilder error() {
		return ShinyError.builder();
	}

	public static ShinyTableBuilder table() {
		return ShinyTable.builder();
	}

	public static ShinyBarChartBuilder barChart() {
		return ShinyBarChart.builder();
	}

	public static ShinyProgressBarBuilder progressBar() {
		return ShinyProgressBar.builder();
	}

	public static ShinySpinnerBuilder spinner() {
		return ShinySpinner.builder();
	}

	public static ShinyTreeBuilder tree(final String label) {
		return ShinyTree.builder().withLabel(label);
	}

	public static ShinyGaugeBuilder gauge() {
		return ShinyGauge.builder();
	}

	public static ShinySparklineBuilder sparkline() {
		return ShinySparkline.builder();
	}

	public static ShinyStatusBuilder status() {
		return ShinyStatus.builder();
	}

	public static ShinyFigletBuilder figlet() {
		return ShinyFiglet.builder();
	}

	public static ShinyCalendarBuilder calendar() {
		return ShinyCalendar.builder();
	}

	public static ShinyTextPathBuilder textPath() {
		return ShinyTextPath.builder();
	}

	public static ShinyJsonBuilder json() {
		return ShinyJson.builder();
	}

	public static ShinyListBuilder list() {
		return ShinyList.builder();
	}

	public static ShinyTitleBuilder title() {
		return ShinyTitle.builder();
	}

	//	public static ShinyMarkDownBuilder markdown() {
	//		return ShinyMarkDown.builder();
	//	}

	public static ShinyParagraphBuilder paragraph() {
		return ShinyParagraph.builder();
	}

	public static ShinyToggleBuilder toggle() {
		return ShinyToggle.builder();
	}

	public static ShinyRatingBuilder rating() {
		return ShinyRating.builder();
	}

	public static ShinyMultiSelectionBuilder multiSelection() {
		return ShinyMultiSelection.builder();
	}

	public static ShinyInputTextBuilder inputText() {
		return ShinyInputText.builder();
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
