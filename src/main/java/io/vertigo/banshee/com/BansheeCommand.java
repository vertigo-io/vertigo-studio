package io.vertigo.banshee.com;

import java.util.List;
import java.util.UUID;

import io.vertigo.shiny.models.ShinyProp;

public record BansheeCommand(
		String command,
		String id, //facultatif id d'un model
		List<ShinyProp> props) {
}
