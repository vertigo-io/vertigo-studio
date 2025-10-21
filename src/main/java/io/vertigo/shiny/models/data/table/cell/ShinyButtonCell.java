package io.vertigo.shiny.models.data.table.cell;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;

public record ShinyButtonCell(
		UUID id,
		String text,
		String color,
		String action) implements ShinyTableCell {

	public ShinyButtonCell {
		Assertion.check().isNotNull(id)
				.isNotBlank(text)
				.isNotBlank(action);
	}

	@Override
	public String shinyType() {
		return "ShinyButtonCell";
	}
}
