package io.vertigo.banshee.com;

import io.vertigo.shiny.models.ShinyModel;

public record BansheeResult(
		BansheeAction action,
		//	UUID id,
		ShinyModel model) {
}
