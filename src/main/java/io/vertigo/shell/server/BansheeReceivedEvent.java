package io.vertigo.shell.server;

import java.util.List;
import java.util.UUID;

import io.vertigo.shiny.models.ShinyProp;

public record BansheeReceivedEvent(
		String command,
		UUID id, //facultatif 
		List<ShinyProp> props) {
}
