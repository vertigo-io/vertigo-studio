package io.vertigo.shiny.models.data.table.cell;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;

public record ShinyBadgeCell(
		UUID id,
		String content,
		String color) implements ShinyTableCell {

	public ShinyBadgeCell {
		Assertion.check().isNotNull(id);
		Assertion.check().isNotBlank(content);
	}

	@Override
	public String shinyType() {
		return "ShinyBadgeCell";
	}
}
