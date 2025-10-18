package io.vertigo.shiny.data.timeline;

import java.util.List;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyTimeline(
		String title,
		List<ShinyTimelineItem> items) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyTimeline";
	}
}
