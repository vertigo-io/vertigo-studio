package io.vertigo.shiny.models.media.image;

import io.vertigo.shiny.models.ShinyBlock;

public record ShinyImage(
		String title,
		String url,
		String alt) implements ShinyBlock {

}
