package io.vertigo.shiny.models.media.youtube;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyYoutubeBuilder implements Builder<ShinyYoutube> {
	private UUID _id;
	private String _title;
	private String _videoId;

	public ShinyYoutubeBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyYoutubeBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		this._title = title;
		return this;
	}

	public ShinyYoutubeBuilder withVideoId(final String videoId) {
		Assertion.check().isNotBlank(videoId);
		this._videoId = videoId;
		return this;
	}

	@Override
	public ShinyYoutube build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyYoutube(_id, _title, _videoId);
	}
}