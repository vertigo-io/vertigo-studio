package io.vertigo.shiny.models.media.rss;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyRssBuilder implements Builder<ShinyRss> {
	private UUID _id;
	private String _title;
	private final List<ShinyRssItem> _items = new ArrayList<>();

	public ShinyRssBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

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

	@Override
	public ShinyRss build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyRss(_id, _title, _items);
	}
}
