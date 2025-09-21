package io.vertigo.shiny.components.live.progressbar;

import java.util.UUID;

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
	@JsonProperty
	public final String id = UUID.randomUUID().toString();
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

	public int getTotal() {
		return total;
	}

	public int getValue() {
		return value;
	}

	public ShinyProgressBarStyle getProgressBarStyle() {
		return progressBarStyle;
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
	synchronized protected void draw(final ShinyWriter writer) {
		new ShinyProgressBarRenderer().render(this, writer);
	}

}
