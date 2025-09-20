package io.vertigo.shiny.components.live.progressbar;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyComponentRenderer; // New import
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import

public final class ShinyProgressBarRenderer implements ShinyComponentRenderer { // Implements interface

	public ShinyProgressBarRenderer() { // Public no-arg constructor
		//private constructor
	}

	@Override // Override annotation
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyProgressBar;
	}

	@Override // Override annotation
	public synchronized void render(final ShinyProgressBar shinyProgressBar, final ShinyWriter writer) { // Not static
		Assertion.check().isNotNull(shinyProgressBar);
		Assertion.check().isNotNull(writer);
		//---
		// Calculer le pourcentage
		final int percentage = (shinyProgressBar.getValue() * 100) / shinyProgressBar.getTotal();
		// Calculer le nombre de carrés à remplir
		final int filled = (shinyProgressBar.getValue() * shinyProgressBar.getProgressBarStyle().maxLength()) / shinyProgressBar.getTotal();

		// Construire la barre
		writer.print("\r")
				.print("[")
				.print("█".repeat(filled))
				.print("▒".repeat(shinyProgressBar.getProgressBarStyle().maxLength() - filled))
				.print("] ")
				.print(percentage + "%")
				.flush(); //On force le flush
	}
}
