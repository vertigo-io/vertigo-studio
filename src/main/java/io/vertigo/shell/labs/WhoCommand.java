package io.vertigo.shell.labs;

import java.net.InetAddress;

import com.beust.jcommander.Parameters;

@Parameters(commandNames = "who", commandDescription = "Displays the current user's IP address.")
public class WhoCommand implements Runnable {
	public void run() {
		try {
			final InetAddress localHost = InetAddress.getLocalHost();
			System.out.println("Adresse IP : " + localHost.getHostAddress());
		} catch (Exception e) {
			System.err.println("Impossible d'obtenir l'adresse IP.");
		}
	}
}
