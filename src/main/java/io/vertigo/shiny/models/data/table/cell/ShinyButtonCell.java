package io.vertigo.shiny.models.data.table.cell;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import jakarta.annotation.Nonnull;

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
