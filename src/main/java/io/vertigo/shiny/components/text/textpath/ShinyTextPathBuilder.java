package io.vertigo.shiny.components.text.textpath;

import java.nio.file.Path;

import io.vertigo.core.lang.Builder;

public final class ShinyTextPathBuilder implements Builder<ShinyTextPath> {
	private String textPath;
	private String textPathSeparator = "/";

	public ShinyTextPathBuilder withPath(final Path path) {
		this.textPath = path.normalize().toString();
		return this;
	}

	public ShinyTextPathBuilder withPath(final String path) {
		this.textPath = path;
		return this;
	}

	public ShinyTextPathBuilder withSeparator(final String separator) {
		this.textPathSeparator = separator;
		return this;
	}

	@Override
	public ShinyTextPath build() {
		return new ShinyTextPath(textPath, textPathSeparator);
	}
}
