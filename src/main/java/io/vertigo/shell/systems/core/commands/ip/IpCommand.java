package io.vertigo.shell.systems.core.commands.ip;

import java.net.InetAddress;
import java.net.UnknownHostException;

import io.vertigo.shell.ShellCommand;
import picocli.CommandLine.Command;

@Command(name = "ip", description = "Displays the local host's IP address.")

public class IpCommand implements ShellCommand {

	@Override
	public void run() {
		try {
			final InetAddress localHost = InetAddress.getLocalHost();
			System.out.println("IP Address: " + localHost.getHostAddress());
		} catch (final UnknownHostException e) {
			System.err.println("Unable to obtain the IP address.");
		}
	}
}
