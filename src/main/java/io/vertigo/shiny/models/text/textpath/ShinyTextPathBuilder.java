package io.vertigo.shiny.models.text.textpath;

import java.nio.file.Path;

import io.vertigo.core.lang.Builder;

public final class ShinyTextPathBuilder implements Builder<ShinyTextPath> {
	    private String _textPath;
	    private String _textPathSeparator = "/";
	public ShinyTextPathBuilder withPath(final Path path) {
		        this._textPath = path.normalize().toString();		return this;
	}

	public ShinyTextPathBuilder withPath(final String path) {
		        this._textPath = path;		return this;
	}

	public ShinyTextPathBuilder withSeparator(final String separator) {
		        this._textPathSeparator = separator;		return this;
	}

	@Override
	    public ShinyTextPath build() {
	        return new ShinyTextPath(_textPath, _textPathSeparator);
	    }}
