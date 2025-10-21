package io.vertigo.shiny.models.media.image;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyImageBuilder implements Builder<ShinyImage> {
	private UUID _id;
	private String _title;
	private String _url;
	private String _alt;

	public ShinyImageBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyImageBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyImageBuilder withUrl(final String url) {
		Assertion.check().isNotBlank(url);
		this._url = url;
		return this;
	}

	public ShinyImageBuilder withAlt(final String alt) {
		this._alt = alt;
		return this;
	}

	@Override
	public ShinyImage build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyImage(_id, _title, _url, _alt);
	}
}
