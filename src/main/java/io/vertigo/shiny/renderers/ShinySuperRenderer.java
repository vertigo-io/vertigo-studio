package io.vertigo.shiny.renderers;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.data.calendar.ShinyCalendarRenderer;
import io.vertigo.shiny.components.data.json.ShinyJsonRenderer;
import io.vertigo.shiny.components.data.list.ShinyListRenderer;
import io.vertigo.shiny.components.data.table.ShinyTableRenderer;
import io.vertigo.shiny.components.data.tree.ShinyTreeRenderer;
import io.vertigo.shiny.components.dataviz.barchart.ShinyBarChartRenderer;
import io.vertigo.shiny.components.dataviz.gauge.ShinyGaugeRenderer;
import io.vertigo.shiny.components.dataviz.rating.ShinyRatingRenderer;
import io.vertigo.shiny.components.dataviz.sparkline.ShinySparklineRenderer;
import io.vertigo.shiny.components.dataviz.status.ShinyStatusRenderer;
import io.vertigo.shiny.components.error.ShinyErrorRenderer;
import io.vertigo.shiny.components.input.multiselection.ShinyMultiSelectionRenderer;
import io.vertigo.shiny.components.input.text.ShinyInputTextRenderer;
import io.vertigo.shiny.components.text.figlet.ShinyFigletRenderer;
import io.vertigo.shiny.components.text.paragraph.ShinyParagraphRenderer;
import io.vertigo.shiny.components.text.textpath.ShinyTextPathRenderer;
import io.vertigo.shiny.components.text.title.ShinyTitleRenderer;
import io.vertigo.shiny.components.text.toggle.ShinyToggleRenderer;

public final class ShinySuperRenderer {

	private final List<ShinyComponentRenderer<? extends ShinyComponent>> renderers;

	public ShinySuperRenderer() {
		this.renderers = List.of(
				//---Data
				new ShinyCalendarRenderer(),
				new ShinyJsonRenderer(),
				new ShinyListRenderer(),
				new ShinyTableRenderer(),
				new ShinyTreeRenderer(),
				//---Dataviz
				new ShinyBarChartRenderer(),
				new ShinyGaugeRenderer(),
				new ShinyRatingRenderer(),
				new ShinySparklineRenderer(),
				new ShinyStatusRenderer(),
				//---Error
				new ShinyErrorRenderer(),
				//---Input
				new ShinyMultiSelectionRenderer(),
				new ShinyInputTextRenderer(),
				//---text
				new ShinyFigletRenderer(),
				new ShinyParagraphRenderer(),
				new ShinyTextPathRenderer(),
				new ShinyTitleRenderer(),
				new ShinyToggleRenderer());
	}

	public void render(final ShinyComponent component, final ShinyWriter writer) {
		Assertion.check().isNotNull(component);
		Assertion.check().isNotNull(writer);
		//---
		for (final ShinyComponentRenderer<? extends ShinyComponent> renderer : renderers) {
			if (renderer.accept(component)) {
				// We know the type is correct because accept returned true
				((ShinyComponentRenderer<ShinyComponent>) renderer).render(component, writer);
				return;
			}
		}
		throw new IllegalArgumentException("No renderer found for component type: " + component.getClass().getName());
	}
}
