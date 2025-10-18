package io.vertigo.shiny.models.data.chip;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyChip(
		String text,
		String color,
		ShinyChipVariant variant,
		boolean closable,
		String icon) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyChip";
	}

	public ShinyChip {
		Assertion.check()
				.isNotBlank(text, "Text cannot be blank");
	}
}
