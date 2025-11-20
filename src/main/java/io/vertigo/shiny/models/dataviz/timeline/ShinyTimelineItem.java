package io.vertigo.shiny.models.dataviz.timeline;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;

public record ShinyTimelineItem(
		@Nonnull String title,
		String content,
		String color,
		String icon) {

	public ShinyTimelineItem {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
	}
}
