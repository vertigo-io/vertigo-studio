package io.vertigo.shiny.components.live.spinner;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;

public final class ShinySpinnerRenderer {

	private ShinySpinnerRenderer() {
		//private constructor
	}

	public static synchronized void render(final ShinySpinner shinySpinner, final ShinyWriter writer) {
		Assertion.check().isNotNull(shinySpinner);
		Assertion.check().isNotNull(writer);
		//---
		final var frame = shinySpinner.getSpinnerStyle().frames()[shinySpinner.getFrameIndex() % shinySpinner.getSpinnerStyle().frames().length];
		writer.print("\r")
				.print(frame)
				.print(" " + shinySpinner.getMessage())
				.flush(); //On force le flush
		shinySpinner.setFrameIndex(shinySpinner.getFrameIndex() + 1);
	}
}
