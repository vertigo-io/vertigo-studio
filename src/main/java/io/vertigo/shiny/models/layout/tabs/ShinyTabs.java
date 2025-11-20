package io.vertigo.shiny.models.layout.tabs;

import java.util.List;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyTabs(@Nonnull List<ShinyTab> tabs) implements ShinyBlock {
	public ShinyTabs {
		Assertion.check()
				.isNotNull(tabs);
	}
}
