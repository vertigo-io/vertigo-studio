package io.vertigo.shiny.components.data.list;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Builder;

public final class ShinyListBuilder implements Builder<ShinyList> {
	private String _title;
	private final List<Object> _items = new ArrayList<>();
	private ShinyListType _listType = ShinyListType.UNORDERED;

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
		return new ShinyList(_title, _items, _listType);
	}
}
