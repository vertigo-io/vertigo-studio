package io.vertigo.shell.server;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

public record BansheeEvent(
		BansheeAction action,
		UUID id,
		ShinyModel model) {
}
