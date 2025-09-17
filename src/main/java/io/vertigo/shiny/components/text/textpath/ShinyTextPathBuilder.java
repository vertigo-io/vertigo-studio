package io.vertigo.shiny.components.text.textpath;

import java.nio.file.Path;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinyTextPathBuilder implements Builder<ShinyTextPath> {
	String textPath;
	String separator = "/";
	ShinyTextPathStyle textPathStyle;

	// No public constructor, use ShinyTextPath.builder()
	ShinyTextPathBuilder() {
		// Package-private constructor
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

	public ShinyTextPathBuilder withSeparator(final String pathSeparator) {
		this.separator = pathSeparator;
		return this;
	}

	@Override
	public ShinyTextPath build() {
		// Perform any final validations here before building the object
		Assertion.check().isNotBlank(textPath, "Path cannot be blank");
		//---
		return new ShinyTextPath(this);
	}
}
