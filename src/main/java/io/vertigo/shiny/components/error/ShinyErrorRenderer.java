package io.vertigo.shiny.components.error;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import
import io.vertigo.shiny.renderers.ShinyComponentRenderer;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyErrorRenderer implements ShinyComponentRenderer<ShinyError> {

	@Override
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyError;
	}

	@Override
	public void render(final ShinyError shinyError, final ShinyWriter writer) {
		Assertion.check().isNotNull(shinyError);
		Assertion.check().isNotNull(writer);
		//---
		writer.println(ShinyColors.RED.fg("!> " + shinyError.text() + " "));
	}
}
