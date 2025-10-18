package io.vertigo.shiny.models.media.rss;

import java.util.List;
import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyRss(
		UUID id,
		String title,
		List<ShinyRssItem> items) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyRss";
	}
}
