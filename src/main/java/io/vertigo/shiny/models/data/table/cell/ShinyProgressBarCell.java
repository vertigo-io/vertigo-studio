package io.vertigo.shiny.models.data.table.cell;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;

public record ShinyProgressBarCell(
		UUID id,
		double value,
		double maxValue,
		String color) implements ShinyTableCell {

	public ShinyProgressBarCell {
		Assertion.check()
				.isNotNull(id)
				.isTrue(value >= 0, "Value must be positive")
				.isTrue(maxValue > 0, "Max value must be positive")
				.isTrue(value <= maxValue, "Value must be less than or equal to max value");
	}

	@Override
	public String shinyType() {
		return "ShinyProgressBarCell";
	}
}
