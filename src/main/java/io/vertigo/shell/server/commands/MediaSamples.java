package io.vertigo.shell.server.commands;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.media.rss.ShinyRssItem;

public class MediaSamples {
	public static class PdfSample {
		public ShinyComponent execute() {
			try {
				return Shiny.pdf()
						.withTitle("Arthur Rimbaud - Poèmes")
						.withPath("sample-report.pdf")
						.build();
			} catch (Exception e) {
				return Shiny.error()
						.withText("Failed to download PDF: " + e.getMessage())
						.build();
			}
		}
	}

	public static class RssSample {
		public ShinyComponent execute() {
			try {
				final URL feedUrl = new URL("https://www.francetvinfo.fr/titres.rss");
				final SyndFeedInput input = new SyndFeedInput();
				final SyndFeed feed = input.build(new XmlReader(feedUrl));
				List<ShinyRssItem> items = new ArrayList<>();
				for (final SyndEntry entry : feed.getEntries()) {
					items.add(new ShinyRssItem(
							entry.getTitle(),
							entry.getLink(),
							entry.getDescription().getValue(),
							entry.getEnclosures().isEmpty() ? null : entry.getEnclosures().getFirst().getUrl(),
							entry.getPublishedDate().toString(),
							entry.getAuthor()));
				}
				return Shiny.rss()
						.withTitle(feed.getTitle())
						.addAllItems(items)
						.build();
			} catch (final Exception e) {
				return Shiny.error().withText("Erreur lors de la récupération du flux RSS : " + e.getMessage()).build();
			}
		}
	}
}
