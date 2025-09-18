package io.vertigo.shiny.components.live.progressbar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.live.ShinyLiveComponent;

public final class ShinyProgressBar extends ShinyLiveComponent<ShinyProgressBar> {
	@JsonProperty
	private final int total; // Valeur totale correspondant à 100%
	@JsonProperty
	private volatile int value;
	@JsonIgnore
	private final ShinyProgressBarStyle progressBarStyle;

	// Package-private constructor, only accessible by the Builder
	ShinyProgressBar(ShinyProgressBarBuilder builder) {
		super();
		Assertion.check()
				.isNotNull(builder);
		//---
		this.total = builder.total;
		this.progressBarStyle = builder.progressBarStyle;
	}

	// Static factory method to get a new Builder instance
	public static ShinyProgressBarBuilder builder() {
		return new ShinyProgressBarBuilder();
	}

	// Méthode pour mettre à jour la progression
	public void liveUpdate(final int progress) {
		if (progress < 0) {
			this.value = 0;
		} else if (progress > total) {
			this.value = 100;
		} else {
			this.value = progress;
		}
	}

	synchronized protected void draw(ShinyWriter writer) {
		// Calculer le pourcentage
		final int percentage = (value * 100) / total;
		// Calculer le nombre de carrés à remplir
		final int filled = (value * progressBarStyle.maxLength()) / total;

		// Construire la barre
		writer.print("\r")
				.print("[")
				.print("█".repeat(filled))
				.print("▒".repeat(progressBarStyle.maxLength() - filled))
				.print("] ")
				.print(percentage + "%")
				.flush(); //On force le flush
	}

}
