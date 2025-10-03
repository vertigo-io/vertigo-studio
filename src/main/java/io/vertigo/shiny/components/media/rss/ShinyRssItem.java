package io.vertigo.shiny.components.media.rss;

public record ShinyRssItem(
		String title,
		String link,
		String description,
		String image,
		String pubDate,
		String author) {

}
