package io.vertigo.shiny.renderers.live;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.live.spinner.ShinySpinner;
import io.vertigo.shiny.renderers.ShinyModelRenderer;

public final class ShinySpinnerRenderer implements ShinyModelRenderer<ShinySpinner> {

	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinySpinner;
	}

	@Override
	public void render(final ShinySpinner shinySpinner) {
		Assertion.check()
				.isNotNull(shinySpinner);
		//---
		final ShinySpinnerStyle style = Shiny.theme().spinnerStyle();

		final var frame = style.frames()[shinySpinner.getFrameIndex() % style.frames().length];

		Shiny.writer().print("\r")
				.print(frame)
				.print(" " + shinySpinner.getMessage())
				.flush(); //On force le flush
		shinySpinner.setFrameIndex(shinySpinner.getFrameIndex() + 1);
	}
}
