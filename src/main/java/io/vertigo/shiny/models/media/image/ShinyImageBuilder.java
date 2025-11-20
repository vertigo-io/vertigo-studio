package io.vertigo.shiny.models.media.image;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import jakarta.annotation.Nonnull;

public final class ShinyImageBuilder implements Builder<ShinyImage> {

	private String _title;
	private String _url;
	private String _alt;

	public ShinyImageBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyImageBuilder withUrl(@Nonnull final String url) {
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
		return new ShinyImage(_title, _url, _alt);
	}
}
