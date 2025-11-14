package io.vertigo.shiny.models.layout.tabs;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public final class ShinyTabsBuilder {
	private final List<ShinyTab> _tabs = new ArrayList<>();

	public ShinyTabsBuilder addTab(final String title, final ShinyBlock content) {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(content);
		//---
		_tabs.add(new ShinyTab(title, content));
		return this;
	}

	public ShinyTabs build() {
		return new ShinyTabs(_tabs);
	}
}
