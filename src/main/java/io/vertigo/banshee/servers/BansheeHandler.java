package io.vertigo.banshee.servers;

import java.util.function.Consumer;

public interface BansheeHandler {
	void handle(Consumer<String> webSocket, String event);
}
