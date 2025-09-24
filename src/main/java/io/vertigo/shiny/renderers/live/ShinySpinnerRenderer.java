package io.vertigo.shiny.renderers.live;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.live.spinner.ShinySpinner;
import io.vertigo.shiny.renderers.ShinyComponentRenderer;

public final class ShinySpinnerRenderer implements ShinyComponentRenderer<ShinySpinner> {

	@Override
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinySpinner;
	}

	@Override
	public void render(final ShinySpinner shinySpinner) {
		Assertion.check()
				.isNotNull(shinySpinner);
		//---
		final ShinySpinnerStyle style = Shiny.theme().spinnerStyle();
		final ShinyWriter writer = Shiny.writer();

		final var frame = style.frames()[shinySpinner.getFrameIndex() % style.frames().length];
		writer.print("\r")
				.print(frame)
				.print(" " + shinySpinner.getMessage())
				.flush(); //On force le flush
		shinySpinner.setFrameIndex(shinySpinner.getFrameIndex() + 1);
	}
}
