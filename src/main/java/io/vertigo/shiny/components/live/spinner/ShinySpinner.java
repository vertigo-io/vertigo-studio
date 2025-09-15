package io.vertigo.shiny.components.live.spinner;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.live.ShinyLiveComponent;

/**
 * This component doesn't accept color. 
 */
public final class ShinySpinner extends ShinyLiveComponent<ShinySpinner> {
	private volatile String message;
	private volatile int frameIndex = 0;

	private ShinySpinnerStyle spinnerStyle;

	// Constructeur
	public ShinySpinner() {
		super();
		this.spinnerStyle = Shiny.theme().spinnerStyle();
	}

	public ShinySpinner style(final ShinySpinnerStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.spinnerStyle = style;
		return this;
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
	synchronized protected void draw(ShinyWriter writer) {
		final var frame = spinnerStyle.frames()[frameIndex % spinnerStyle.frames().length];
		writer.print("\r")
				.print(frame)
				.print(" " + message)
				.flush(); //On force le flush
		frameIndex++;
	}
}
