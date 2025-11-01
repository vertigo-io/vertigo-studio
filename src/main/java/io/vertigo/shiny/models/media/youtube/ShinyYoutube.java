package io.vertigo.shiny.models.media.youtube;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyYoutube(
		String title,
		String videoId) implements ShinyBlock {

	public ShinyYoutube {
		Assertion.check()
				.isNotBlank(videoId, "Video ID cannot be blank");
	}
}
