package io.vertigo.shiny.components.text.markdown;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyMarkDownBuilder implements Builder<ShinyMarkDown> {
	private String markdownText; // Renamed from _markdownText

	// No public constructor, use ShinyMarkDown.builder()
	ShinyMarkDownBuilder() {
		// Package-private constructor
	}

	public ShinyMarkDownBuilder withFile(final String path) {
		try {
			this.markdownText = Files.readString(Path.of(path));
		} catch (final IOException e) {
			throw new RuntimeException("Error reading file: " + path, e);
		}
		return this;
	}

	public ShinyMarkDownBuilder withText(final String text) {
		this.markdownText = text;
		return this;
	}

	// Getter for the field, needed by the record constructor
	String markdownText() {
		return markdownText;
	}

	@Override
	public ShinyMarkDown build() {
		// Perform any final validations here before building the object
		Assertion.check().isNotNull(markdownText, "Markdown text not set. Use fromFile() or fromText().");
		//---
		return new ShinyMarkDown(this);
	}
}