package io.vertigo.shiny.renderers.core;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import
import io.vertigo.shiny.components.core.container.ShinyContainer;
import io.vertigo.shiny.renderers.ShinyComponentRenderer;

public final class ShinyContainerRenderer implements ShinyComponentRenderer<ShinyContainer> {

	@Override
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyContainer;
	}

	@Override
	public void render(final ShinyContainer container) {
		Assertion.check()
				.isNotNull(container);
		//---
		//		final ShinyContainerStyle style = Shiny.theme().containerStyle();
		final ShinyWriter writer = Shiny.writer();

		for (ShinyComponent component : container.components()) {
			Shiny.render(component);
			writer.println("-------");
		}
	}
}
