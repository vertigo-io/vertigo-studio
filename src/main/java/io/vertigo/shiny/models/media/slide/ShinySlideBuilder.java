package io.vertigo.shiny.models.media.slide;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

/**
 * Builder for creating instances of ShinySlide.
 * A ShinySlide represents a slide component that displays Markdown content.
 */
public final class ShinySlideBuilder implements Builder<ShinySlide> {
	private String _markdown;

	/**
	 * Sets the Markdown content for the slides from a file.
	 * @param path The path to the Markdown file.
	 * @return This builder instance.
	 * @throws RuntimeException if an error occurs while reading the file.
	 */
	public ShinySlideBuilder withFile(final String path) {
		try {
			this._markdown = Files.readString(Path.of(path));
		} catch (final IOException e) {
			throw new RuntimeException("Error reading file: " + path, e);
		}
		return this;
	}

	/**
	 * Sets the Markdown content directly for the slides.
	 * @param markdown The Markdown content string.
	 * @return This builder instance.
	 */
	public ShinySlideBuilder withMarkdown(final String markdown) {
		this._markdown = markdown;
		return this;
	}

	/**
	 * Builds a new ShinySlide instance using the configured properties.
	 * Asserts that Markdown content has been set.
	 * @return A new ShinySlide instance.
	 */
	@Override
	public ShinySlide build() {
		Assertion.check().isNotNull(_markdown, "Markdown not set. Use withFile() or withMarkdown().");
		return new ShinySlide(_markdown);
	}
}
