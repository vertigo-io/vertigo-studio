package io.vertigo.shiny.components.media.rss;

import java.util.List;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyRss(
		String title,
		List<ShinyRssItem> items) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinyRss";
	}
}
