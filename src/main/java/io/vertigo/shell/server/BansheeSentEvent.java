package io.vertigo.shell.server;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

public record BansheeSentEvent(
		BansheeAction action,
		UUID id,
		ShinyModel model) {
}
