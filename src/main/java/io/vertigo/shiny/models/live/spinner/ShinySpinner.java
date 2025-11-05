package io.vertigo.shiny.models.live.spinner;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.live.ShinyLiveComponent;
import io.vertigo.shiny.renderers.live.ShinySpinnerRenderer;

/**
 * This component doesn't accept color. 
 */
public final class ShinySpinner extends ShinyLiveComponent<ShinySpinner> {
	private volatile String message;
	private volatile int frameIndex = 0;

	// Package-private constructor, only accessible by the Builder
	ShinySpinner(String message) {
		super();
		Assertion.check()
				.isNotBlank(message);
		//---
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public int getFrameIndex() {
		return frameIndex;
	}

	public void setFrameIndex(final int frameIndex) {
		this.frameIndex = frameIndex;
	}

	// Static factory method to get a new Builder instance
	public static ShinySpinnerBuilder builder() {
		return new ShinySpinnerBuilder();
	}

	/**
	 * Sends a new message to be displayed alongside the spinner.
	 * @param msg The message to display.
	 */
	public void liveSend(final String msg) {
		this.message = msg;
	}

	/**
	 * Draws the current frame of the spinner with the message.
	 */
	@Override
	synchronized protected void draw() {
		new ShinySpinnerRenderer().render(this);
	}

}
