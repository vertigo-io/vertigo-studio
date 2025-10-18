package io.vertigo.shell.systems.core.commands.ip;

import java.net.InetAddress;
import java.net.UnknownHostException;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import picocli.CommandLine.Command;

@Command(name = "ip", description = "Displays the local host's IP address.")

public class IpCommand implements ShellCommand {

	@Override
	public ShinyModel build() {
		try {
			final InetAddress localHost = InetAddress.getLocalHost();
			return Shiny.paragraph()
					.withText("IP Address: " + localHost.getHostAddress())
					.build();
		} catch (final UnknownHostException e) {
			return Shiny.error()
					.withText("Unable to obtain the IP address.")
					.build();
		}
	}
}
