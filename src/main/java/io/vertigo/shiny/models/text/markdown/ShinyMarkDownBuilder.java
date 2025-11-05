package io.vertigo.shiny.models.text.markdown;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyMarkDownBuilder implements Builder<ShinyMarkDown> {
	private String _markdownText;

	public ShinyMarkDownBuilder withFile(final String path) {
		try {
			this._markdownText = Files.readString(Path.of(path));
		} catch (final IOException e) {
			throw new RuntimeException("Error reading file: " + path, e);
		}
		return this;
	}

	public ShinyMarkDownBuilder withText(final String text) {
		this._markdownText = text;
		return this;
	}

	// Getter for the field, needed by the record constructor
	String markdownText() {
		return _markdownText;
	}

	@Override
	public ShinyMarkDown build() {
		// Perform any final validations here before building the object
		Assertion.check().isNotNull(_markdownText, "Markdown text not set. Use fromFile() or fromText()."); //---
		return new ShinyMarkDown(this._markdownText);
	}
}
