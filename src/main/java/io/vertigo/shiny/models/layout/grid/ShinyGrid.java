package io.vertigo.shiny.models.layout.grid;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import io.vertigo.shiny.models.ShinyLayout;

public record ShinyGrid(
		@Nonnull UUID id,
		int columns,
		@Nonnull List<ShinyBlock> content) implements ShinyLayout {

	public ShinyGrid {
		Assertion.check()
				.isNotNull(id)
				.isTrue(columns > 0, "Grid must have at least one column")
				.isNotNull(content);
	}
}
