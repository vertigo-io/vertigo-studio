package io.vertigo.studio.shell.labs;

import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Clears the console.")
public class ClearCommand implements Runnable {
	public void run() {
		System.out.print("\033[H\033[2J"); // Efface console dans certains terminaux
		System.out.flush();
	}
}
