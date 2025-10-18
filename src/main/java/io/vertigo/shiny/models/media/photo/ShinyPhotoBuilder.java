package io.vertigo.shiny.models.media.photo;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyPhotoBuilder implements Builder<ShinyPhoto> {
	private UUID _id;
	private String _title;
	private String _url;
	private String _alt;

	public ShinyPhotoBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyPhotoBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyPhotoBuilder withUrl(final String url) {
		Assertion.check().isNotBlank(url);
		this._url = url;
		return this;
	}

	public ShinyPhotoBuilder withAlt(final String alt) {
		this._alt = alt;
		return this;
	}

	@Override
	public ShinyPhoto build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyPhoto(_id, _title, _url, _alt);
	}
}