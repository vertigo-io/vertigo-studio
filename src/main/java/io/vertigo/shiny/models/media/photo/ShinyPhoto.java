package io.vertigo.shiny.models.media.photo;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyPhoto(
		String title,
		String url,
		String alt) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyPhoto";
	}

	public ShinyPhoto {
		Assertion.check()
				.isNotBlank(url, "URL cannot be blank");
	}
}
