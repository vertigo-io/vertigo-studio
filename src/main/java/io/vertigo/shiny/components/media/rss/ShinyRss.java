package io.vertigo.shiny.components.media.rss;

import java.util.List;

import io.vertigo.shiny.ShinyType;
import io.vertigo.shiny.components.ShinyComponent;

@ShinyType("rss")
public record ShinyRss(
		String title,
		List<ShinyRssItem> items) implements ShinyComponent {
}