package io.vertigo.shiny.renderers.live;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import
import io.vertigo.shiny.components.live.progressbar.ShinyProgressBar;
import io.vertigo.shiny.renderers.ShinyComponentRenderer;

public final class ShinyProgressBarRenderer implements ShinyComponentRenderer<ShinyProgressBar> {
	@Override
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyProgressBar;
	}

	@Override
	public void render(final ShinyProgressBar shinyProgressBar) {
		Assertion.check()
				.isNotNull(shinyProgressBar);
		//---
		final ShinyProgressBarStyle style = Shiny.theme().progressBarStyle();
		final ShinyWriter writer = Shiny.writer();
		// Calculer le pourcentage
		final int percentage = (shinyProgressBar.getValue() * 100) / shinyProgressBar.getTotal();
		// Calculer le nombre de carrés à remplir
		final int filled = (shinyProgressBar.getValue() * style.maxLength()) / shinyProgressBar.getTotal();

		// Construire la barre
		writer.print("\r")
				.print("[")
				.print("█".repeat(filled))
				.print("▒".repeat(style.maxLength() - filled))
				.print("] ")
				.print(percentage + "%")
				.flush(); //On force le flush
	}
}
