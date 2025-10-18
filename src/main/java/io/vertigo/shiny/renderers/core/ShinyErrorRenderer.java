package io.vertigo.shiny.renderers.core;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.core.error.ShinyError;
import io.vertigo.shiny.renderers.ShinyModelRenderer;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyErrorRenderer implements ShinyModelRenderer<ShinyError> {

	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinyError;
	}

	@Override
	public void render(final ShinyError shinyError) {
		Assertion.check()
				.isNotNull(shinyError);
		//---
		//final ShinyErrorStyle style = Shiny.theme().errorStyle();
		final ShinyWriter writer = Shiny.writer();
		writer.println(ShinyColors.RED.fg("!> " + shinyError.text() + " "));
	}
}
