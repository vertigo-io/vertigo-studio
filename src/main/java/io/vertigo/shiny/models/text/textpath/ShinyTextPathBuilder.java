package io.vertigo.shiny.models.text.textpath;

import java.nio.file.Path;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyTextPathBuilder implements Builder<ShinyTextPath> {
	private UUID _id;
	private String _textPath;
	private String _textPathSeparator = "/";

	public ShinyTextPathBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyTextPathBuilder withPath(final Path path) {
		Assertion.check().isNotNull(path);
		this._textPath = path.normalize().toString();
		return this;
	}

	public ShinyTextPathBuilder withPath(final String path) {
		Assertion.check().isNotBlank(path);
		this._textPath = path;
		return this;
	}

	public ShinyTextPathBuilder withSeparator(final String separator) {
		Assertion.check().isNotBlank(separator);
		this._textPathSeparator = separator;
		return this;
	}

	@Override
	public ShinyTextPath build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyTextPath(_id, _textPath, _textPathSeparator);
	}
}
