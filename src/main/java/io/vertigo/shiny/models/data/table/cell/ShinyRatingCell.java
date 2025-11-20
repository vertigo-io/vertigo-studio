package io.vertigo.shiny.models.data.table.cell;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.text.rating.ShinyRatingScale;
import jakarta.annotation.Nonnull;

public record ShinyRatingCell(
		@Nonnull UUID id,
		double value,
		@Nonnull ShinyRatingScale scale,
		boolean allowHalfRating) implements ShinyTableCell {

	public ShinyRatingCell {
		Assertion.check()
				.isNotNull(id)
				.isTrue(value >= 0, "Value must be positive")
				.isNotNull(scale);
	}

	@Override
	public String shinyType() {
		return "ShinyRatingCell";
	}
}
