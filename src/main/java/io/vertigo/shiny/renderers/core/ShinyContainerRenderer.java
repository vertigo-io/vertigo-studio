package io.vertigo.shiny.renderers.core;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.core.container.ShinyContainer;
import io.vertigo.shiny.renderers.ShinyComponentRenderer;

public final class ShinyContainerRenderer implements ShinyComponentRenderer<ShinyContainer> {

	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinyContainer;
	}

	@Override
	public void render(final ShinyContainer container) {
		Assertion.check()
				.isNotNull(container);
		//---
		//		final ShinyContainerStyle style = Shiny.theme().containerStyle();
		final ShinyWriter writer = Shiny.writer();

		for (ShinyModel component : container.components()) {
			Shiny.render(component);
			writer.println("-------");
		}
	}
}
