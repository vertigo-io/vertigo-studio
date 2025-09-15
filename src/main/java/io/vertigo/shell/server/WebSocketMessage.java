package io.vertigo.shell.server;

public record WebSocketMessage(
		String type,
		String data) {
}
