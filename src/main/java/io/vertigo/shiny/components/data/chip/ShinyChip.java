package io.vertigo.shiny.components.data.chip;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyChip(
		String text,
		String color,
		ShinyChipVariant variant,
		boolean closable,
		String icon) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinyChip";
	}

	public ShinyChip {
		Assertion.check()
				.isNotBlank(text, "Text cannot be blank");
	}
}
