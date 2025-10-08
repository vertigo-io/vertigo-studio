package io.vertigo.shiny;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import io.vertigo.shiny.components.core.container.ShinyContainerBuilder;
import io.vertigo.shiny.components.core.error.ShinyErrorBuilder;
import io.vertigo.shiny.components.data.calendar.ShinyCalendarBuilder;
import io.vertigo.shiny.components.data.card.ShinyCardBuilder;
import io.vertigo.shiny.components.data.form.ShinyFormBuilder;
import io.vertigo.shiny.components.data.json.ShinyJsonBuilder;
import io.vertigo.shiny.components.data.list.ShinyListBuilder;
import io.vertigo.shiny.components.data.table.ShinyTableBuilder;
import io.vertigo.shiny.components.data.tree.ShinyTreeBuilder;
import io.vertigo.shiny.components.dataviz.chart.ShinyChartBuilder;
import io.vertigo.shiny.components.dataviz.chart.ShinyChartType;
import io.vertigo.shiny.components.dataviz.gauge.ShinyGaugeBuilder;
import io.vertigo.shiny.components.dataviz.rating.ShinyRatingBuilder;
import io.vertigo.shiny.components.dataviz.sankey.ShinySankeyBuilder;
import io.vertigo.shiny.components.dataviz.sparkline.ShinySparklineBuilder;
import io.vertigo.shiny.components.dataviz.status.ShinyStatusBuilder;
import io.vertigo.shiny.components.input.multiselection.ShinyMultiSelectionBuilder;
import io.vertigo.shiny.components.input.text.ShinyInputTextBuilder;
import io.vertigo.shiny.components.live.progressbar.ShinyProgressBarBuilder;
import io.vertigo.shiny.components.live.spinner.ShinySpinnerBuilder;
import io.vertigo.shiny.components.media.geomap.ShinyGeoMapBuilder;
import io.vertigo.shiny.components.media.pdf.ShinyPdfComponentBuilder;
import io.vertigo.shiny.components.media.photo.ShinyPhotoBuilder;
import io.vertigo.shiny.components.media.rss.ShinyRssBuilder;
import io.vertigo.shiny.components.media.youtube.ShinyYoutubeBuilder;
import io.vertigo.shiny.components.text.figlet.ShinyFigletBuilder;
import io.vertigo.shiny.components.text.paragraph.ShinyParagraphBuilder;
import io.vertigo.shiny.components.text.textpath.ShinyTextPathBuilder;
import io.vertigo.shiny.components.text.title.ShinyTitleBuilder;
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
		return new ShinyErrorBuilder();
	}

	public static ShinyContainerBuilder container() {
		return new ShinyContainerBuilder();
	}

	public static ShinyTableBuilder table() {
		return new ShinyTableBuilder();
	}

	public static ShinyProgressBarBuilder progressBar() {
		return new ShinyProgressBarBuilder();
	}

	public static ShinySpinnerBuilder spinner() {
		return new ShinySpinnerBuilder();
	}

	public static ShinyTreeBuilder tree() {
		return new ShinyTreeBuilder();
	}

	public static ShinyGaugeBuilder gauge() {
		return new ShinyGaugeBuilder();
	}

	public static ShinySparklineBuilder sparkline() {
		return new ShinySparklineBuilder();
	}

	public static ShinyFormBuilder form() {
		return new ShinyFormBuilder();
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

	public static ShinyPdfComponentBuilder pdf() {
		return new ShinyPdfComponentBuilder();
	}

	public static ShinyCardBuilder card() {
		return new ShinyCardBuilder();
	} //	public static ShinyComposer composer() {
		//		return new ShinyComposer();
		//	}

	//---dataviz
	public static ShinyChartBuilder barChart() {
		return new ShinyChartBuilder(ShinyChartType.bar);
	}

	public static ShinyChartBuilder radarChart() {
		return new ShinyChartBuilder(ShinyChartType.radar);
	}

	public static ShinyChartBuilder areaChart() {
		return new ShinyChartBuilder(ShinyChartType.area);
	}

	public static ShinyChartBuilder lineChart() {
		return new ShinyChartBuilder(ShinyChartType.line);
	}

	public static ShinyChartBuilder donutChart() {
		return new ShinyChartBuilder(ShinyChartType.donut);
	}

	public static ShinyChartBuilder pieChart() {
		return new ShinyChartBuilder(ShinyChartType.pie);
	}

	public static ShinyPhotoBuilder photo() {
		return new ShinyPhotoBuilder();
	}

	public static ShinyGeoMapBuilder geoMap() {
		return new ShinyGeoMapBuilder();
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

	public static ShinyYoutubeBuilder youtube() {
		return new ShinyYoutubeBuilder();
	}

	public static ShinyRssBuilder rss() {
		return new ShinyRssBuilder();
	}

	public static ShinySankeyBuilder sankey() {
		return new ShinySankeyBuilder();
	}

	//	private static ShinySuperRenderer RENDERER = new ShinySuperRenderer();
	//
	//	public static <S extends ShinyComponent> void render(S component) {
	//		RENDERER.render(component);
	//	}
}
