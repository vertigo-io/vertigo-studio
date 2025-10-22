package io.vertigo.shiny.models.dataviz.timeline;

import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyTimeline(
		UUID id,
		String title,
		List<ShinyTimelineItem> items) implements ShinyModel {

	public ShinyTimeline {

	}

}
