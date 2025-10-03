package io.vertigo.shell.server;

import java.io.IOException;

public class Banshee {

	public static void main(String[] args) throws IOException {
		int webServerPort = 8081; // Using port 8081 to avoid conflict
		int webSocketPort = 8080;
		String rootDir = "src/main/java/io/vertigo/shell/server/";

		new BansheeWebServerBuilder(webServerPort, rootDir).build().start();
		new BansheeWebSocketServer(webSocketPort).start();
	}
}
