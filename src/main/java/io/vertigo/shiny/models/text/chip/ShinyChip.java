package io.vertigo.shiny.models.text.chip;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;
import jakarta.annotation.Nonnull;

public record ShinyChip(
		@Nonnull String text,
		String color,
		ShinyChipVariant variant,
		boolean closable,
		String icon) implements ShinyModel {

	public ShinyChip {
		Assertion.check()
				.isNotBlank(text, "Text cannot be blank");
	}
}
