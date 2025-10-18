package io.vertigo.shiny.models.data.timeline;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyTimelineBuilder implements Builder<ShinyTimeline> {
	private UUID _id;
	private String _title;
	private final List<ShinyTimelineItem> _items = new ArrayList<>();

	public ShinyTimelineBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyTimelineBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyTimelineBuilder addItem(final ShinyTimelineItem item) {
		_items.add(item);
		return this;
	}

	public ShinyTimelineBuilder addItem(final String title, final String content) {
		return addItem(new ShinyTimelineItem(title, content, null, null));
	}

	public ShinyTimelineBuilder addItem(final String title, final String content, final String color) {
		return addItem(new ShinyTimelineItem(title, content, color, null));
	}

	public ShinyTimelineBuilder addItem(final String title, final String content, final String color, final String icon) {
		_items.add(new ShinyTimelineItem(title, content, color, icon));
		return this;
	}

	public ShinyTimeline build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyTimeline(_id, _title, _items);
	}
}
