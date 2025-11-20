package io.vertigo.banshee;

import java.io.IOException;

import io.vertigo.banshee.samples.BansheeSamples;
import io.vertigo.banshee.servers.BansheeWebServerBuilder;
import io.vertigo.banshee.servers.BansheeWebSocketServer;

public final class Banshee {

	public static void main(String[] args) throws IOException {
		int webServerPort = 8081;
		int webSocketPort = 8080;
		String rootDir = "src/main/java/io/vertigo/shell/server/";

		new BansheeWebServerBuilder(webServerPort, rootDir).build().start();
		new BansheeWebSocketServer(webSocketPort, BansheeSamples.commandHandler).start();
	}
}
