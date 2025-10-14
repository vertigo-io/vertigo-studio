package io.vertigo.shiny.components.media.youtube;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyYoutube(
		String title,
		String videoId) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinyYoutube";
	}


	public ShinyYoutube {
		Assertion.check()
				.isNotBlank(videoId, "Video ID cannot be blank");
	}
}