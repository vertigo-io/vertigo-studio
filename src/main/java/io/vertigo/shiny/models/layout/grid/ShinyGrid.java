package io.vertigo.shiny.models.layout.grid;

import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import io.vertigo.shiny.models.ShinyLayout;

public record ShinyGrid(
		UUID id,
		int columns,
		List<ShinyBlock> content) implements ShinyLayout {

	public ShinyGrid {
		Assertion.check()
				.isNotNull(id)
				.isTrue(columns > 0, "Grid must have at least one column")
				.isNotNull(content);
	}

	@Override
	public String shinyType() {
		return "ShinyGrid";
	}
}
