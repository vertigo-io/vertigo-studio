package io.vertigo.shell.shiny.spinner;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;

public final class ShinySpinner implements AutoCloseable, ShinyComponent {
	private final Shiny shiny;
	private volatile boolean running = true;
	private volatile String message;
	private final SpinnerDrawer drawer;

	public ShinySpinner(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
		drawer = new SpinnerDrawer();
		drawer.start();
	}

	/**
	 * Stops the spinner animation.
	 */
	@Override
	public void close() {
		running = false;
		try {
			drawer.join();
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Sends a new message to be displayed alongside the spinner.
	 * @param msg The message to display.
	 */
	public void send(final String msg) {
		message = msg;
	}

	/**
	 * Inner class that extends Thread to handle the spinner animation in a loop.
	 */
	private class SpinnerDrawer extends Thread {

		@Override
		public void run() {
			int i = 0;
			while (running) {
				draw(i++);
				try {
					Thread.sleep(100); // Adjust sleep time for animation speed
				} catch (final InterruptedException e) {
					Thread.currentThread().interrupt();
					running = false;
				}
			}
			// Print a newline to move to the next line after stopping
			shiny.getWriter().println();
		}

		/**
		 * Draws the current frame of the spinner with the message.
		 */
		private void draw(final int i) {
			final var frame = ShinySpinnerStyle.FRAMES[i % ShinySpinnerStyle.FRAMES.length];
			shiny.getWriter().print("\r");
			shiny.getWriter().print(frame);
			shiny.getWriter().print(" " + message);
			shiny.getWriter().flush(); //On force le flush
		}
	}

}
