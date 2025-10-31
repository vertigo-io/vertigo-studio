package io.vertigo.banshee.com;

import io.vertigo.shiny.models.ShinyModel;

public record BansheeResult(
		BansheeAction action, //create or update
		ShinyModel model) {
}
