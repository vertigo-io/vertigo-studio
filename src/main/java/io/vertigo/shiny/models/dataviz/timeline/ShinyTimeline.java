package io.vertigo.shiny.models.dataviz.timeline;

import java.util.List;
import java.util.UUID;

import io.vertigo.shiny.models.ShinyBlock;

public record ShinyTimeline(
		UUID id,
		String title,
		List<ShinyTimelineItem> items) implements ShinyBlock {

	public ShinyTimeline {

	}

}
