package io.vertigo.shiny.models.layout.page;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyLayout;

public final class ShinyPageBuilder {
	private String _title;
	private ShinyLayout _layout;

	public ShinyPageBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		_title = title;
		return this;
	}

	public ShinyPageBuilder withLayout(final ShinyLayout layout) {
		Assertion.check().isNotNull(layout);
		//---
		_layout = layout;
		return this;
	}

	public ShinyPage build() {
		return new ShinyPage(_title, _layout);
	}
}
