package io.vertigo.shiny.models.media.youtube;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import jakarta.annotation.Nonnull;

public final class ShinyYoutubeBuilder implements Builder<ShinyYoutube> {

	private String _title;
	private String _videoId;

	public ShinyYoutubeBuilder withTitle(@Nonnull final String title) {
		Assertion.check().isNotBlank(title);
		this._title = title;
		return this;
	}

	public ShinyYoutubeBuilder withVideoId(@Nonnull final String videoId) {
		Assertion.check().isNotBlank(videoId);
		this._videoId = videoId;
		return this;
	}

	@Override
	public ShinyYoutube build() {
		return new ShinyYoutube(_title, _videoId);
	}
}
