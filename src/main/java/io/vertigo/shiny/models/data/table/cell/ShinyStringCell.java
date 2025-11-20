package io.vertigo.shiny.models.data.table.cell;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import jakarta.annotation.Nonnull;

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
