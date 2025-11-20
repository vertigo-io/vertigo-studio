package io.vertigo.shiny.models.layout.tabs;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import jakarta.annotation.Nonnull;

public record ShinyTabs(@Nonnull List<ShinyTab> tabs) implements ShinyBlock {
	public ShinyTabs {
		Assertion.check()
				.isNotNull(tabs);
	}
}
