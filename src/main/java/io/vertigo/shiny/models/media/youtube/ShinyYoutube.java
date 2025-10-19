package io.vertigo.shiny.models.media.youtube;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyYoutube(
		UUID id,
		String title,
		String videoId) implements ShinyModel {

	public ShinyYoutube {
		Assertion.check()
				.isNotBlank(videoId, "Video ID cannot be blank");
	}
}
