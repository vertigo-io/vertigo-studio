package io.vertigo.shiny.renderers.live;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyRenderer;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.live.progressbar.ShinyProgressBar;
import io.vertigo.shiny.renderers.ShinyModelRenderer;

public final class ShinyProgressBarRenderer implements ShinyModelRenderer<ShinyProgressBar> {
	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinyProgressBar;
	}

	@Override
	public void render(final ShinyProgressBar shinyProgressBar) {
		Assertion.check()
				.isNotNull(shinyProgressBar);
		//---
		final ShinyProgressBarStyle style = ShinyRenderer.theme().progressBarStyle();
		// Calculer le pourcentage
		final int percentage = (shinyProgressBar.getValue() * 100) / shinyProgressBar.getTotal();
		// Calculer le nombre de carrés à remplir
		final int filled = (shinyProgressBar.getValue() * style.maxLength()) / shinyProgressBar.getTotal();

		// Construire la barre
		ShinyRenderer.writer().print("\r")
				.print("[")
				.print("█".repeat(filled))
				.print("▒".repeat(style.maxLength() - filled))
				.print("] ")
				.print(percentage + "%")
				.flush(); //On force le flush
	}
}
