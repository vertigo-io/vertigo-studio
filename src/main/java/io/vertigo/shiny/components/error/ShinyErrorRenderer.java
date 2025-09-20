package io.vertigo.shiny.components.error;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyErrorRenderer {

	private ShinyErrorRenderer() {
		//private constructor
	}

	public static void render(final ShinyError shinyError, final ShinyWriter writer) {
		Assertion.check().isNotNull(shinyError);
		Assertion.check().isNotNull(writer);
		//---
		writer.println(ShinyColors.RED.fg("!> " + shinyError.text() + " "));
	}
}
