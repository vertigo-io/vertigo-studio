package io.vertigo.shell.shiny;

import io.vertigo.shell.shiny.progressbar.ShinyProgressBar;

public class ShinyProgressBarTest {
	public static void main(String[] args) {
		test();
	}

	private static void test() {
		final ShinyProgressBar progressBar = Shiny.progressBar().total(100);
		System.out.println();
		for (int i = 0; i < 100; i++) {
			progressBar.setProgress(i + 1);
			progressBar.print();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}
		progressBar.finish();
	}
}
