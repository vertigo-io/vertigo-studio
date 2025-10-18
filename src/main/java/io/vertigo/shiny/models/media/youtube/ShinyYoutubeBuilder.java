package io.vertigo.shiny.models.media.youtube;

import io.vertigo.core.lang.Builder;

public final class ShinyYoutubeBuilder implements Builder<ShinyYoutube> {
	private String _title;
	private String _videoId;

	public ShinyYoutubeBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyYoutubeBuilder withVideoId(final String videoId) {
		this._videoId = videoId;
		return this;
	}

	@Override
	public ShinyYoutube build() {
		return new ShinyYoutube(_title, _videoId);
	}
}