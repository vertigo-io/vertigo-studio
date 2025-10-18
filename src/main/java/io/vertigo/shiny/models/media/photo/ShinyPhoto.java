package io.vertigo.shiny.models.media.photo;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyPhoto(
		UUID id,
		String title,
		String url,
		String alt) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyPhoto";
	}

}
