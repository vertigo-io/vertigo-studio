package io.vertigo.shiny.models.layout.page;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import io.vertigo.shiny.models.ShinyLayout;

public record ShinyPage(String title, ShinyLayout layout) implements ShinyBlock {
	public ShinyPage {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(layout);
	}
}
