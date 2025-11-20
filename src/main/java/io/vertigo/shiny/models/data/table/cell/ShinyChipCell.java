package io.vertigo.shiny.models.data.table.cell;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.text.chip.ShinyChipVariant;
import jakarta.annotation.Nonnull;

public record ShinyChipCell(
		@Nonnull UUID id,
		@Nonnull String text,
		String color,
		ShinyChipVariant variant,
		boolean closable,
		String icon) implements ShinyTableCell {

	public ShinyChipCell {
		Assertion.check()
				.isNotNull(id)
				.isNotBlank(text);
	}

	@Override
	public String shinyType() {
		return "ShinyChipCell";
	}
}
