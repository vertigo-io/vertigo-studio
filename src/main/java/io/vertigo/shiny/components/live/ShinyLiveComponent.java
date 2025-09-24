package io.vertigo.shiny.components.live;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public abstract class ShinyLiveComponent<S extends ShinyLiveComponent<S>> implements ShinyComponent, AutoCloseable {
	@JsonIgnore
	private final ShinyLiveDrawer drawer;
	@JsonIgnore
	private ShinyWriter shinyWriter;

	// Constructeur
	public ShinyLiveComponent() {
		drawer = new ShinyLiveDrawer();
	}

	public final S start(ShinyWriter writer) {
		this.shinyWriter = writer;
		drawer.start();
		return (S) this;
	}

	protected abstract void draw(ShinyWriter writer);

	public final void close() {
		drawer.running = false;
		draw(this.shinyWriter);
		try {
			drawer.join();
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		this.shinyWriter.println() // Final newline
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
				draw(shinyWriter);
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
