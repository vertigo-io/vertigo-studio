package io.vertigo.shiny.components.media.rss;

import java.util.List;

public record ShinyRssData(
		String title,
		List<ShinyRssItem> items) {
}
