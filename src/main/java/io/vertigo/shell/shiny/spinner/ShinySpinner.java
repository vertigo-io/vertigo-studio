package io.vertigo.shell.shiny.spinner;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;

public final class ShinySpinner implements AutoCloseable {
	private volatile boolean running = true;
	private volatile String message;
	private final SpinnerDrawer drawer;

	public ShinySpinner(Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		drawer = new SpinnerDrawer();
		drawer.start();
	}

	/**
	 * Stops the spinner animation.
	 */
	public void close() {
		running = false;
		try {
			drawer.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Sends a new message to be displayed alongside the spinner.
	 * @param msg The message to display.
	 */
	public void send(String msg) {
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
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					running = false;
				}
			}
			// Print a newline to move to the next line after stopping
			System.out.println();
		}

		/**
		 * Draws the current frame of the spinner with the message.
		 */
		private void draw(int i) {
			var frame = ShinySpinnerStyle.FRAMES[i % ShinySpinnerStyle.FRAMES.length];
			System.out.print("\r");
			System.out.print(frame);
			System.out.print(" " + message);
		}
	}

}
