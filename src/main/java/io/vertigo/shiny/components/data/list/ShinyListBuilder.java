package io.vertigo.shiny.components.data.list;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Builder;

public final class ShinyListBuilder implements Builder<ShinyList> {
	private String listTitle;
	private final List<Object> listItems = new ArrayList<>();
	private ShinyListType listType = ShinyListType.UNORDERED;

	public ShinyListBuilder withTitle(final String title) {
		this.listTitle = title;
		return this;
	}

	public ShinyListBuilder addItem(final String item) {
		listItems.add(item);
		return this;
	}

	public ShinyListBuilder addAllItems(final List<String> items) {
		this.listItems.addAll(items);
		return this;
	}

	public ShinyListBuilder addList(final ShinyList nestedList) {
		listItems.add(nestedList);
		return this;
	}

	public ShinyListBuilder withType(final ShinyListType type) {
		this.listType = type;
		return this;
	}

	@Override
	public ShinyList build() {
		return new ShinyList(listTitle, listItems, listType);
	}
}
