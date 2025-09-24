package io.vertigo.shiny.renderers.live;

import io.vertigo.core.lang.Assertion;

public final class ShinyProgressBarStyle {
	private int progressBarMaxLength = 50;

	public ShinyProgressBarStyle maxLength(int maxLength) {
		Assertion.check()
				.isTrue(maxLength > 1, "maxlength mus be positive");
		//---
		this.progressBarMaxLength = maxLength;
		return this;
	}

	int maxLength() {
		return progressBarMaxLength;
	}
}
