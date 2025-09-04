package io.vertigo.shiny.components.live.spinner;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.live.ShinyLiveComponent;

public final class ShinySpinner extends ShinyLiveComponent<ShinySpinner> {
	private volatile String message;
	private volatile int frameIndex = 0;

	public ShinySpinner(final Shiny shiny) {
		super(shiny);
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
	synchronized protected void draw() {
		final var frame = ShinySpinnerStyle.FRAMES[frameIndex % ShinySpinnerStyle.FRAMES.length];
		shiny().getWriter().print("\r");
		shiny().getWriter().print(frame);
		shiny().getWriter().print(" " + message);
		shiny().getWriter().flush(); //On force le flush
		frameIndex++;
	}
}
