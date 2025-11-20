package io.vertigo.shiny.models.data.table.cell;

import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;

public record ShinyButtonCell(
		@Nonnull UUID id,
		@Nonnull String text,
		String color,
		@Nonnull String action) implements ShinyTableCell {

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
