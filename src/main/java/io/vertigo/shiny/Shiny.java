package io.vertigo.shiny;

import io.vertigo.shiny.mermaid.ShinyMermaidServer;
import io.vertigo.shiny.models.block.ShinyContainerBuilder;
import io.vertigo.shiny.models.data.board.ShinyBoardBuilder;
import io.vertigo.shiny.models.data.board.ShinyBoardCardBuilder;
import io.vertigo.shiny.models.data.board.ShinyBoardListBuilder;
import io.vertigo.shiny.models.data.card.ShinyCardBuilder;
import io.vertigo.shiny.models.data.datagrid.ShinyDataGridBuilder;
import io.vertigo.shiny.models.data.json.ShinyJsonBuilder;
import io.vertigo.shiny.models.data.list.ShinyListBuilder;
import io.vertigo.shiny.models.data.table.ShinyTableBuilder;
import io.vertigo.shiny.models.data.tree.ShinyTreeBuilder;
import io.vertigo.shiny.models.dataviz.calendar.ShinyCalendarBuilder;
import io.vertigo.shiny.models.dataviz.chart.ShinyChartBuilder;
import io.vertigo.shiny.models.dataviz.chart.ShinyChartType;
import io.vertigo.shiny.models.dataviz.flow.ShinyFlowBuilder;
import io.vertigo.shiny.models.dataviz.gauge.ShinyGaugeBuilder;
import io.vertigo.shiny.models.dataviz.geomap.ShinyGeoMapBuilder;
import io.vertigo.shiny.models.dataviz.mindmap.ShinyMindMapBuilder;
import io.vertigo.shiny.models.dataviz.organization.ShinyOrganizationBuilder;
import io.vertigo.shiny.models.dataviz.sankey.ShinySankeyBuilder;
import io.vertigo.shiny.models.dataviz.timeline.ShinyTimelineBuilder;
import io.vertigo.shiny.models.feedback.alert.ShinyAlertBuilder;
import io.vertigo.shiny.models.feedback.error.ShinyErrorBuilder;
import io.vertigo.shiny.models.feedback.modal.ShinyModalBuilder;
import io.vertigo.shiny.models.feedback.notification.ShinyNotificationBuilder;
import io.vertigo.shiny.models.input.autocomplete.ShinyAutocompleteBuilder;
import io.vertigo.shiny.models.input.codeeditor.ShinyCodeEditorBuilder;
import io.vertigo.shiny.models.input.datepicker.ShinyDatePickerBuilder;
import io.vertigo.shiny.models.input.fileupload.ShinyFileUploadBuilder;
import io.vertigo.shiny.models.input.form.ShinyFormBuilder;
import io.vertigo.shiny.models.input.multiselection.ShinyMultiSelectionBuilder;
import io.vertigo.shiny.models.input.rangeslider.ShinyRangeSliderBuilder;
import io.vertigo.shiny.models.input.slider.ShinySliderBuilder;
import io.vertigo.shiny.models.input.text.ShinyInputTextBuilder;
import io.vertigo.shiny.models.input.toggle.ShinyToggleBuilder;
import io.vertigo.shiny.models.layout.grid.ShinyGridBuilder;
import io.vertigo.shiny.models.layout.page.ShinyPageBuilder;
import io.vertigo.shiny.models.layout.stepper.ShinyStepperBuilder;
import io.vertigo.shiny.models.layout.tabs.ShinyTabsBuilder;
import io.vertigo.shiny.models.live.progressbar.ShinyProgressBarBuilder;
import io.vertigo.shiny.models.live.spinner.ShinySpinnerBuilder;
import io.vertigo.shiny.models.media.image.ShinyImageBuilder;
import io.vertigo.shiny.models.media.pdf.ShinyPdfBuilder;
import io.vertigo.shiny.models.media.rss.ShinyRssBuilder;
import io.vertigo.shiny.models.media.slide.ShinySlideBuilder;
import io.vertigo.shiny.models.media.youtube.ShinyYoutubeBuilder;
import io.vertigo.shiny.models.text.chip.ShinyChipBuilder;
import io.vertigo.shiny.models.text.figlet.ShinyFigletBuilder;
import io.vertigo.shiny.models.text.paragraph.ShinyParagraphBuilder;
import io.vertigo.shiny.models.text.rating.ShinyRatingBuilder;
import io.vertigo.shiny.models.text.sparkline.ShinySparklineBuilder;
import io.vertigo.shiny.models.text.status.ShinyStatusBuilder;
import io.vertigo.shiny.models.text.textpath.ShinyTextPathBuilder;
import io.vertigo.shiny.models.text.title.ShinyTitleBuilder;

public final class Shiny {
	private static final Shiny INSTANCE = new Shiny();

	private Shiny() {
	}

	public static ShinyErrorBuilder error() {
		return new ShinyErrorBuilder();
	}

	public static ShinyAlertBuilder alert() {
		return new ShinyAlertBuilder();
	}

	public static ShinyContainerBuilder container() {
		return new ShinyContainerBuilder();
	}

	public static ShinyTableBuilder table() {
		return new ShinyTableBuilder();
	}

	public static ShinySlideBuilder slide() {
		return new ShinySlideBuilder();
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

	public static ShinyTimelineBuilder timeline() {
		return new ShinyTimelineBuilder();
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

	public static ShinySliderBuilder slider() {
		return new ShinySliderBuilder();
	}

	public static ShinyDatePickerBuilder datePicker() {
		return new ShinyDatePickerBuilder();
	}

	public static ShinyFileUploadBuilder fileUpload() {
		return new ShinyFileUploadBuilder();
	}

	public static ShinyCodeEditorBuilder codeEditor() {
		return new ShinyCodeEditorBuilder();
	}

	public static ShinyNotificationBuilder notification() {
		return new ShinyNotificationBuilder();
	}

	public static ShinyModalBuilder modal() {
		return new ShinyModalBuilder();
	}

	public static ShinyAutocompleteBuilder autocomplete() {
		return new ShinyAutocompleteBuilder();
	}

	public static ShinyPdfBuilder pdf() {
		return new ShinyPdfBuilder();
	}

	public static ShinyRangeSliderBuilder rangeSlider() {
		return new ShinyRangeSliderBuilder();
	}

	public static ShinyCardBuilder card() {
		return new ShinyCardBuilder();
	}

	public static ShinyChipBuilder chip() {
		return new ShinyChipBuilder();
	}

	public static ShinyOrganizationBuilder organization() {
		return new ShinyOrganizationBuilder();
	}

	public static ShinyGridBuilder grid() {
		return new ShinyGridBuilder();
	}

	public static ShinyTabsBuilder tabs() {
		return new ShinyTabsBuilder();
	}

	public static ShinyStepperBuilder stepper() {
		return new ShinyStepperBuilder();
	}

	public static ShinyPageBuilder page() {
		return new ShinyPageBuilder();
	}

	//	public static ShinyComposer composer() {
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

	public static ShinyImageBuilder image() {
		return new ShinyImageBuilder();
	}

	public static ShinyGeoMapBuilder geoMap() {
		return new ShinyGeoMapBuilder();
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

	public static ShinyMindMapBuilder mindMap() {
		return new ShinyMindMapBuilder();
	}

	public static ShinyFlowBuilder flow() {
		return new ShinyFlowBuilder();
	}

	public static ShinyBoardBuilder board() {
		return new ShinyBoardBuilder();
	}

	public static ShinyBoardListBuilder boardList() {
		return new ShinyBoardListBuilder();
	}

	public static ShinyBoardCardBuilder boardCard() {
		return new ShinyBoardCardBuilder();
	}

	public static ShinyDataGridBuilder dataGrid() {
		return new ShinyDataGridBuilder();
	}
}
