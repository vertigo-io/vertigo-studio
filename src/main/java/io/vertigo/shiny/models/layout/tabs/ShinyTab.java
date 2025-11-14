package io.vertigo.shiny.models.layout.tabs;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyTab(String title, ShinyBlock content) {
	public ShinyTab {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(content);
	}
}
