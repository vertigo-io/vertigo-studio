package io.vertigo.shell.shiny.live;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;

public abstract class ShinyLiveComponent<S extends ShinyLiveComponent<S>> implements AutoCloseable {
	private final ShinyLiveDrawer drawer;
	private final Shiny shiny;

	// Constructeur
	public ShinyLiveComponent(Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		drawer = new ShinyLiveDrawer();
		this.shiny = shiny;
	}

	protected final Shiny shiny() {
		return shiny;
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
		shiny.getWriter().println(); // Final newline
		shiny.getWriter().flush();
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
