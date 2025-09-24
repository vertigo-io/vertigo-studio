package io.vertigo.shiny;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.core.container.ShinyContainerBuilder;
import io.vertigo.shiny.components.core.error.ShinyErrorBuilder;
import io.vertigo.shiny.components.data.calendar.ShinyCalendarBuilder;
import io.vertigo.shiny.components.data.chakra.ShinyChakraTableBuilder;
import io.vertigo.shiny.components.data.json.ShinyJsonBuilder;
import io.vertigo.shiny.components.data.list.ShinyListBuilder;
import io.vertigo.shiny.components.data.table.ShinyTableBuilder;
import io.vertigo.shiny.components.data.tree.ShinyTreeBuilder;
import io.vertigo.shiny.components.dataviz.barchart.ShinyBarChartBuilder;
import io.vertigo.shiny.components.dataviz.chakra.ShinyChakraAreaChartBuilder;
import io.vertigo.shiny.components.dataviz.chakra.ShinyChakraDonutChartBuilder;
import io.vertigo.shiny.components.dataviz.chakra.ShinyChakraPieChartBuilder;
import io.vertigo.shiny.components.dataviz.chakra.ShinyChakraSparkLineBuilder;
import io.vertigo.shiny.components.dataviz.gauge.ShinyGaugeBuilder;
import io.vertigo.shiny.components.dataviz.rating.ShinyRatingBuilder;
import io.vertigo.shiny.components.dataviz.sparkline.ShinySparklineBuilder;
import io.vertigo.shiny.components.dataviz.status.ShinyStatusBuilder;
import io.vertigo.shiny.components.input.multiselection.ShinyMultiSelectionBuilder;
import io.vertigo.shiny.components.input.text.ShinyInputTextBuilder;
import io.vertigo.shiny.components.live.progressbar.ShinyProgressBarBuilder;
import io.vertigo.shiny.components.live.spinner.ShinySpinnerBuilder;
import io.vertigo.shiny.components.text.figlet.ShinyFigletBuilder;
import io.vertigo.shiny.components.text.paragraph.ShinyParagraphBuilder;
import io.vertigo.shiny.components.text.textpath.ShinyTextPathBuilder;
import io.vertigo.shiny.components.text.title.ShinyTitleBuilder;
import io.vertigo.shiny.components.text.toggle.ShinyToggleBuilder;
import io.vertigo.shiny.mermaid.ShinyMermaidServer;
import io.vertigo.shiny.renderers.ShinySuperRenderer;

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
		return new ShinyErrorBuilder();
	}

	public static ShinyContainerBuilder container() {
		return new ShinyContainerBuilder();
	}

	public static ShinyTableBuilder table() {
		return new ShinyTableBuilder();
	}

	public static ShinyChakraTableBuilder chakraTable() {
		return new ShinyChakraTableBuilder();
	}

	public static ShinyBarChartBuilder barChart() {
		return new ShinyBarChartBuilder();
	}

	public static ShinyProgressBarBuilder progressBar() {
		return new ShinyProgressBarBuilder();
	}

	public static ShinySpinnerBuilder spinner() {
		return new ShinySpinnerBuilder();
	}

	public static ShinyTreeBuilder tree(final String label) {
		return new ShinyTreeBuilder().withLabel(label);
	}

	public static ShinyGaugeBuilder gauge() {
		return new ShinyGaugeBuilder();
	}

	public static ShinySparklineBuilder sparkline() {
		return new ShinySparklineBuilder();
	}

	public static ShinyStatusBuilder status() {
		return new ShinyStatusBuilder();
	}

	public static ShinyFigletBuilder figlet() {
		return new ShinyFigletBuilder();
	}

	public static ShinyCalendarBuilder calendar() {
		return new ShinyCalendarBuilder();
	}

	public static ShinyTextPathBuilder textPath() {
		return new ShinyTextPathBuilder();
	}

	public static ShinyJsonBuilder json() {
		return new ShinyJsonBuilder();
	}

	public static ShinyListBuilder list() {
		return new ShinyListBuilder();
	}

	public static ShinyTitleBuilder title() {
		return new ShinyTitleBuilder();
	}

	//	public static ShinyMarkDownBuilder markdown() {
	//		return ShinyMarkDown.builder();
	//	}

	public static ShinyParagraphBuilder paragraph() {
		return new ShinyParagraphBuilder();
	}

	public static ShinyToggleBuilder toggle() {
		return new ShinyToggleBuilder();
	}

	public static ShinyRatingBuilder rating() {
		return new ShinyRatingBuilder();
	}

	public static ShinyMultiSelectionBuilder multiSelection() {
		return new ShinyMultiSelectionBuilder();
	}

	public static ShinyInputTextBuilder inputText() {
		return new ShinyInputTextBuilder();
	}

	public static ShinyChakraSparkLineBuilder chakraSparkLine() {
		return new ShinyChakraSparkLineBuilder();
	}

	public static ShinyChakraPieChartBuilder chakraPieChart() {
		return new ShinyChakraPieChartBuilder();
	}

	public static ShinyChakraDonutChartBuilder chakraDonutChart() {
		return new ShinyChakraDonutChartBuilder();
	}

	public static ShinyChakraAreaChartBuilder chakraAreaChart() {
		return new ShinyChakraAreaChartBuilder();
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

	private static ShinySuperRenderer RENDERER = new ShinySuperRenderer();

	public static void render(ShinyComponent component) {
		RENDERER.render(component, writer());
	}
}
