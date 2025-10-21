package io.vertigo.shiny.models.data.table.cell;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;

public record ShinyStringCell(
		UUID id,
		String value) implements ShinyTableCell {

	public ShinyStringCell {
		Assertion.check()
				.isNotNull(id)
				.isNotNull(value);
	}

	@Override
	public String shinyType() {
		return "ShinyStringCell";
	}
}
