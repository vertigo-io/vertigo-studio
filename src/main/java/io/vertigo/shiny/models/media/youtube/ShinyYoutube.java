package io.vertigo.shiny.models.media.youtube;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyYoutube(
		String title,
		String videoId) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyYoutube";
	}


	public ShinyYoutube {
		Assertion.check()
				.isNotBlank(videoId, "Video ID cannot be blank");
	}
}