package io.vertigo.shiny.components.media.photo;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyPhoto(
		String title,
		String url,
		String alt) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinyPhoto";
	}

	public ShinyPhoto {
		Assertion.check()
				.isNotBlank(url, "URL cannot be blank");
	}
}
