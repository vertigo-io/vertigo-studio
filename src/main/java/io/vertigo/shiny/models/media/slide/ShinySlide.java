package io.vertigo.shiny.models.media.slide;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

/**
 * ShinySlide is a record representing a slide component within the Shiny framework.
 * It is used to display presentation slides defined using Markdown content.
 *
 * @param markdown The Markdown content of the slides.
 */
public record ShinySlide(@Nonnull String markdown) implements ShinyModel {

	/**
	 * Constructor for ShinySlide.
	 * Asserts that the markdown content is not null.
	 */
	public ShinySlide {
		Assertion.check().isNotNull(markdown);
	}

}
