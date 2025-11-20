package io.vertigo.shiny.models.data.table.cell;

import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;

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
