package io.vertigo.shiny.models.media.youtube;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import jakarta.annotation.Nonnull;

public record ShinyYoutube(
		String title,
		@Nonnull String videoId) implements ShinyBlock {

	public ShinyYoutube {
		Assertion.check()
				.isNotBlank(videoId, "Video ID cannot be blank");
	}
}
