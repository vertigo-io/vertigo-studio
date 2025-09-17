package io.vertigo.shiny.components.data.list;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinyListBuilder implements Builder<ShinyList> {
	String title;
	final List<Object> listItems = new ArrayList<>();
	ShinyListType listType = ShinyListType.UNORDERED;
	ShinyListStyle listStyle;

	// No public constructor, use ShinyList.builder()
	ShinyListBuilder() {
		// Package-private constructor
		this.listStyle = Shiny.theme().listStyle(); // Initialize default style
	}

	public ShinyListBuilder withStyle(final ShinyListStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.listStyle = style;
		return this;
	}

	public ShinyListBuilder withTitle(final String text) {
		this.title = text;
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
		// Perform any final validations here before building the object
		// For example, if title is null, it should be handled
		//---
		return new ShinyList(this);
	}
}
