package io.vertigo.shiny.models.text.chip;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyChip(
		String text,
		String color,
		ShinyChipVariant variant,
		boolean closable,
		String icon) implements ShinyModel {

	public ShinyChip {
		Assertion.check()
				.isNotBlank(text, "Text cannot be blank");
	}
}
