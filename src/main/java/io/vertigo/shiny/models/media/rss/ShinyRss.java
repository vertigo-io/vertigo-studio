package io.vertigo.shiny.models.media.rss;

import java.util.List;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyRss(
		String title,
		List<ShinyRssItem> items) implements ShinyModel {
}
