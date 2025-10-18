package io.vertigo.shiny.models.live;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;

public abstract class ShinyLiveComponent<S extends ShinyLiveComponent<S>> implements ShinyModel, AutoCloseable {
	@JsonIgnore
	private final ShinyLiveDrawer drawer;

	// Constructeur
	public ShinyLiveComponent() {
		drawer = new ShinyLiveDrawer();
	}

	public final S start() {
		drawer.start();
		return (S) this;
	}

	protected abstract void draw();

	public final void close() {
		drawer.running = false;
		draw();
		try {
			drawer.join();
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		Shiny.writer().println() // Final newline
				.flush();
	}

	/**
	 * Inner class that extends Thread to handle the progress bar animation in a loop.
	 */
	private class ShinyLiveDrawer extends Thread {
		private volatile boolean running = true;

		@Override
		public void run() {
			while (running) {
				draw();
				try {
					Thread.sleep(100); // Adjust sleep time for animation speed
				} catch (final InterruptedException e) {
					Thread.currentThread().interrupt();
					running = false;
				}
			}
		}
	}
}
