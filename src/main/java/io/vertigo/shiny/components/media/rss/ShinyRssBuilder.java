package io.vertigo.shiny.components.media.rss;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyRssBuilder implements Builder<ShinyRss> {
	private String _title;
	private final List<ShinyRssItem> _items = new ArrayList<>();

	public ShinyRssBuilder withTitle(final String title) {
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

	@Override
	public ShinyRss build() {
		return new ShinyRss(_title, _items);
	}
}
