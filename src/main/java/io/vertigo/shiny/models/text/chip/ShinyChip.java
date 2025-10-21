package io.vertigo.shiny.models.text.chip;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyChip(
		UUID id,
		String text,
		String color,
		ShinyChipVariant variant,
		boolean closable,
		String icon) implements ShinyModel {

	public ShinyChip {
		Assertion.check().isNotNull(id);
		Assertion.check()
				.isNotBlank(text, "Text cannot be blank");
	}
}
