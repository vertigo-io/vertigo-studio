package io.vertigo.shiny.components.live.spinner;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.live.ShinyLiveComponent;

/**
 * This component doesn't accept color. 
 */
public final class ShinySpinner extends ShinyLiveComponent<ShinySpinner> {
	private volatile String message;
	private volatile int frameIndex = 0;

	private final ShinySpinnerStyle spinnerStyle;

	// Package-private constructor, only accessible by the Builder
	ShinySpinner(ShinySpinnerBuilder builder) {
		super();
		Assertion.check()
				.isNotNull(builder);
		//---
		this.message = builder.message;
		this.spinnerStyle = builder.spinnerStyle;
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

	public ShinySpinnerStyle getSpinnerStyle() {
		return spinnerStyle;
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
	synchronized protected void draw(final ShinyWriter writer) {
		new ShinySpinnerRenderer().render(this, writer);
	}
}
