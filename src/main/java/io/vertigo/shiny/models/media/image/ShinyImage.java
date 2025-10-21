package io.vertigo.shiny.models.media.image;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyImage(
		UUID id,
		String title,
		String url,
		String alt) implements ShinyModel {

}
