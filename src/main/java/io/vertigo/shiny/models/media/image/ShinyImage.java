package io.vertigo.shiny.models.media.image;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyImage(
		String title,
		String url,
		String alt) implements ShinyModel {

}
