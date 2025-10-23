package io.vertigo.shiny.models.media.rss;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.core.lang.VSystemException;

public final class ShinyRssBuilder implements Builder<ShinyRss> {

	private String _title;
	private final List<ShinyRssItem> _items = new ArrayList<>();

	public ShinyRssBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		this._title = title;
		return this;
	}

	public ShinyRssBuilder addItem(ShinyRssItem item) {
		Assertion.check().isNotNull(item);
		//---
		this._items.add(item);
		return this;
	}

	public ShinyRssBuilder addAllItems(final List<ShinyRssItem> items) {
		Assertion.check().isNotNull(items);
		//---
		this._items.addAll(items);
		return this;
	}

	public ShinyRssBuilder withFeed(final String feedUrl) {
		Assertion.check().isNotNull(feedUrl);
		//---
		try {
			final SyndFeedInput input = new SyndFeedInput();
			final SyndFeed feed = input.build(new XmlReader(new URL(feedUrl)));
			List<ShinyRssItem> items = new ArrayList<>();
			for (final SyndEntry entry : feed.getEntries()) {
				String imageUrl = null;
				if (!entry.getEnclosures().isEmpty()) {
					imageUrl = entry.getEnclosures().getFirst().getUrl();
				} else {
					imageUrl = entry.getForeignMarkup().stream()
							.filter(element -> ("thumbnail".equals(element.getName()) || "content".equals(element.getName())) && "http://search.yahoo.com/mrss/".equals(element.getNamespaceURI()))
							.map(element -> element.getAttribute("url").getValue())
							.findFirst().orElse(null);
				}
				items.add(new ShinyRssItem(
						entry.getTitle(),
						entry.getLink(),
						entry.getDescription().getValue(),
						imageUrl,
						entry.getPublishedDate().toString(),
						entry.getAuthor()));
			}
			//---
			return this.withTitle(feed.getTitle())
					.addAllItems(items);
		} catch (final Exception e) {
			throw new VSystemException("Erreur lors de la récupération du flux RSS : " + e.getMessage());
		}
	}

	@Override
	public ShinyRss build() {

		return new ShinyRss(null, _title, _items);
	}
}
