package io.vertigo.shiny.models.media.rss;

import java.util.List;

import io.vertigo.shiny.models.ShinyBlock;

public record ShinyRss(
		String title,
		List<ShinyRssItem> items) implements ShinyBlock {
}
