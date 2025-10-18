package io.vertigo.shiny.models.media.photo;

import io.vertigo.core.lang.Builder;

public final class ShinyPhotoBuilder implements Builder<ShinyPhoto> {
	private String _title;
	private String _url;
	private String _alt;

	public ShinyPhotoBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyPhotoBuilder withUrl(final String url) {
		this._url = url;
		return this;
	}

	public ShinyPhotoBuilder withAlt(final String alt) {
		this._alt = alt;
		return this;
	}

	@Override
	public ShinyPhoto build() {
		return new ShinyPhoto(_title, _url, _alt);
	}
}