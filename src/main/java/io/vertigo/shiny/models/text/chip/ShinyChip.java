package io.vertigo.shiny.models.text.chip;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

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
