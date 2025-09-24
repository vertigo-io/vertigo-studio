package io.vertigo.shiny.renderers;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.data.json.ShinyJsonRenderer;
import io.vertigo.shiny.components.data.list.ShinyListRenderer;
import io.vertigo.shiny.components.data.table.ShinyTableRenderer;

public final class ShinySuperRenderer {

	private final List<? extends ShinyComponentRenderer> renderers;

	public ShinySuperRenderer() {
		this.renderers = List.of(
				//				//---Core
				//				new ShinyContainerRenderer(),
				//				new ShinyErrorRenderer(),
				//				//---Data
				//				new ShinyCalendarRenderer(),
				new ShinyJsonRenderer(),
				new ShinyListRenderer(),
				new ShinyTableRenderer()
		//				new ShinyTreeRenderer(),
		//				//---Dataviz
		//				new ShinyBarChartRenderer(),
		//				new ShinyGaugeRenderer(),
		//				new ShinyRatingRenderer(),
		//				new ShinySparklineRenderer(),
		//				new ShinyStatusRenderer(),
		//				//---Input
		//				new ShinyMultiSelectionRenderer(),
		//				new ShinyInputTextRenderer(),
		//				//---text
		//				new ShinyFigletRenderer(),
		//				new ShinyParagraphRenderer(),
		//				new ShinyTextPathRenderer(),
		//				new ShinyTitleRenderer(),
		//				new ShinyToggleRenderer()
		);
	}

	public void render(final ShinyComponent component, final ShinyStyle style, final ShinyWriter writer) {
		Assertion.check().isNotNull(component);
		Assertion.check().isNotNull(writer);
		//---
		for (final ShinyComponentRenderer renderer : renderers) {
			if (renderer.accept(component)) {
				// We know the type is correct because accept returned true
				renderer.render(component, style, writer);
				return;
			}
		}
		throw new IllegalArgumentException("No renderer found for component type: " + component.getClass().getName());
	}
}
