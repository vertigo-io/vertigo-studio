package io.vertigo.shiny.components.media.photo;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyType;
import io.vertigo.shiny.components.ShinyComponent;

@ShinyType("photo")
public record ShinyPhoto(
		String title,
		String url,
		String alt) implements ShinyComponent {

	public ShinyPhoto {
		Assertion.check()
				.isNotBlank(url, "URL cannot be blank");
	}
}