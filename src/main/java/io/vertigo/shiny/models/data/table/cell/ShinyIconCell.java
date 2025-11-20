package io.vertigo.shiny.models.data.table.cell;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import jakarta.annotation.Nonnull;

public record ShinyIconCell(
		@Nonnull UUID id,
		@Nonnull String icon,
		String color,
		String size) implements ShinyTableCell {

	public ShinyIconCell {
		Assertion.check()
				.isNotNull(id)
				.isNotBlank(icon);
	}

	@Override
	public String shinyType() {
		return "ShinyIconCell";
	}
}
