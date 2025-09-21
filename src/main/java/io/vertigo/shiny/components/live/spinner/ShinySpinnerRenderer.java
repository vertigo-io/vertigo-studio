package io.vertigo.shiny.components.live.spinner;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.renderers.ShinyComponentRenderer;

public final class ShinySpinnerRenderer implements ShinyComponentRenderer<ShinySpinner> {

	@Override
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinySpinner;
	}

	@Override
	public synchronized void render(final ShinySpinner shinySpinner, final ShinyWriter writer) {
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
