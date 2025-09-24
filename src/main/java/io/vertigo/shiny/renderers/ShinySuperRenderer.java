package io.vertigo.shiny.renderers;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.renderers.core.ShinyContainerRenderer;
import io.vertigo.shiny.renderers.core.ShinyErrorRenderer;
import io.vertigo.shiny.renderers.data.ShinyCalendarRenderer;
import io.vertigo.shiny.renderers.data.ShinyJsonRenderer;
import io.vertigo.shiny.renderers.data.ShinyListRenderer;
import io.vertigo.shiny.renderers.data.ShinyTableRenderer;
import io.vertigo.shiny.renderers.data.ShinyTreeRenderer;
import io.vertigo.shiny.renderers.dataviz.ShinyBarChartRenderer;
import io.vertigo.shiny.renderers.dataviz.ShinyGaugeRenderer;
import io.vertigo.shiny.renderers.dataviz.ShinyRatingRenderer;
import io.vertigo.shiny.renderers.dataviz.ShinySparklineRenderer;
import io.vertigo.shiny.renderers.dataviz.ShinyStatusRenderer;
import io.vertigo.shiny.renderers.input.ShinyInputTextRenderer;
import io.vertigo.shiny.renderers.input.ShinyMultiSelectionRenderer;
import io.vertigo.shiny.renderers.text.ShinyFigletRenderer;
import io.vertigo.shiny.renderers.text.ShinyParagraphRenderer;
import io.vertigo.shiny.renderers.text.ShinyTextPathRenderer;
import io.vertigo.shiny.renderers.text.ShinyTitleRenderer;
import io.vertigo.shiny.renderers.text.ShinyToggleRenderer;

public final class ShinySuperRenderer {

	private final List<? extends ShinyComponentRenderer> renderers;

	public ShinySuperRenderer() {
		this.renderers = List.of(
				//---Core
				new ShinyContainerRenderer(),
				new ShinyErrorRenderer(),
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

	public void render(final ShinyComponent component) {
		Assertion.check().isNotNull(component);
		//---
		for (final ShinyComponentRenderer renderer : renderers) {
			if (renderer.accept(component)) {
				// We know the type is correct because accept returned true
				renderer.render(component);
				return;
			}
		}
		throw new IllegalArgumentException("No renderer found for component type: " + component.getClass().getName());
	}
}
