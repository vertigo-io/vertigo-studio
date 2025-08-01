package io.vertigo.shell.labs;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.beust.jcommander.Parameters;

import io.vertigo.shell.ShellCommand;

@Parameters(commandNames = "who", commandDescription = "Displays the current user's IP address.")

public class WhoCommand implements ShellCommand {

	public void run() throws Exception {
		try {
			final InetAddress localHost = InetAddress.getLocalHost();
			System.out.println("IP Address: " + localHost.getHostAddress());
		} catch (UnknownHostException e) {
			throw new Exception("Unable to obtain the IP address.", e);
		}
	}

	@Override
	public void reset() {

	}

}
