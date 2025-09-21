package io.vertigo.shiny.components.text.textpath;

import java.nio.file.Path;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinyTextPathBuilder implements Builder<ShinyTextPath> {
	private String textPath;
	private String textPathSeparator = "/";
	private ShinyTextPathStyle textPathStyle;

	public ShinyTextPathBuilder() {
		textPathStyle = Shiny.theme().textPathStyle(); // Initialize default style
	}

	public ShinyTextPathBuilder withStyle(final ShinyTextPathStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.textPathStyle = style;
		return this;
	}

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
		return new ShinyTextPath(textPath, textPathSeparator, textPathStyle);
	}
}
