package io.vertigo.shiny.models.data.list;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Builder;

public final class ShinyListBuilder implements Builder<ShinyList> {
	private UUID _id;
	private String _title;
	private final List<Object> _items = new ArrayList<>();
	private ShinyListType _listType = ShinyListType.UNORDERED;

	public ShinyListBuilder withId(final UUID id) {
		_id = id;
		return this;
	}

	public ShinyListBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyListBuilder addItem(final String item) {
		_items.add(item);
		return this;
	}

	public ShinyListBuilder addAllItems(final List<String> items) {
		this._items.addAll(items);
		return this;
	}

	public ShinyListBuilder addList(final ShinyList nestedList) {
		_items.add(nestedList);
		return this;
	}

	public ShinyListBuilder withType(final ShinyListType type) {
		this._listType = type;
		return this;
	}

	@Override
	public ShinyList build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyList(_id, _title, _items, _listType);
	}
}
