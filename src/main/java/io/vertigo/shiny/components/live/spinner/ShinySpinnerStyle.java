package io.vertigo.shiny.components.live.spinner;

import io.vertigo.core.lang.Assertion;

public final class ShinySpinnerStyle {
	private static final String[] PRETTY_FRAMES = { "⠋", "⠙", "⠹", "⠸", "⠼", "⠴", "⠦", "⠧", "⠇", "⠏" };

	private String[] spinnerFrames = PRETTY_FRAMES;

	String[] frames() {
		return spinnerFrames;
	}

	public ShinySpinnerStyle withFrames(String... frames) {
		Assertion.check()
				.isNotNull(frames)
				.isTrue(frames.length > 1, "you mus register at least 2 frames");
		//---
		this.spinnerFrames = frames;
		return this;
	}
}