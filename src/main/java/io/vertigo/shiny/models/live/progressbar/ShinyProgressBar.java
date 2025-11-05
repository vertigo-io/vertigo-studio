package io.vertigo.shiny.models.live.progressbar;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.vertigo.shiny.models.live.ShinyLiveComponent;
import io.vertigo.shiny.renderers.live.ShinyProgressBarRenderer;

public final class ShinyProgressBar extends ShinyLiveComponent<ShinyProgressBar> {
	@JsonProperty
	private final int total; // Valeur totale correspondant à 100%
	@JsonProperty
	private volatile int value;
	@JsonProperty
	public final String id = UUID.randomUUID().toString();

	// Package-private constructor, only accessible by the Builder
	ShinyProgressBar(final int total) {
		this.total = total;
	}

	public int getTotal() {
		return total;
	}

	public int getValue() {
		return value;
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

	@Override
	synchronized protected void draw() {
		new ShinyProgressBarRenderer().render(this);
	}
}
