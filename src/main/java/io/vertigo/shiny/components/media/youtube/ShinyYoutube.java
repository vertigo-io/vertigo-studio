package io.vertigo.shiny.components.media.youtube;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyType;
import io.vertigo.shiny.components.ShinyComponent;

@ShinyType("youtube")
public record ShinyYoutube(
		String title,
		String videoId) implements ShinyComponent {

	public ShinyYoutube {
		Assertion.check()
				.isNotBlank(videoId, "Video ID cannot be blank");
	}
}