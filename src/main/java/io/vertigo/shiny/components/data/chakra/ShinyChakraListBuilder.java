package io.vertigo.shiny.components.data.chakra;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;

public final class ShinyChakraListBuilder {
	private String myTitle;
	private final List<String> myItems = new ArrayList<>();

	public ShinyChakraListBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		myTitle = title;
		return this;
	}

	public ShinyChakraListBuilder addItem(final String item) {
		Assertion.check().isNotBlank(item);
		//---
		myItems.add(item);
		return this;
	}

	public ShinyChakraList build() {
		return new ShinyChakraList(myTitle, myItems.toArray(String[]::new));
	}
}