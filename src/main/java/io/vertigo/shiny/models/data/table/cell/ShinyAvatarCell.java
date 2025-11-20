package io.vertigo.shiny.models.data.table.cell;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import jakarta.annotation.Nonnull;

public record ShinyAvatarCell(
		@Nonnull UUID id,
		@Nonnull String src,
		String alt,
		String size) implements ShinyTableCell {

	public ShinyAvatarCell {
		Assertion.check()
				.isNotNull(id)
				.isNotBlank(src);
	}

	@Override
	public String shinyType() {
		return "ShinyAvatarCell";
	}
}
