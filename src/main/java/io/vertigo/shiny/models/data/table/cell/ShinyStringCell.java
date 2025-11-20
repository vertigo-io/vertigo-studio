package io.vertigo.shiny.models.data.table.cell;

import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;

public record ShinyStringCell(
		@Nonnull UUID id,
		@Nonnull String value) implements ShinyTableCell {

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
