package io.vertigo.shiny.models.block;

import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import io.vertigo.shiny.models.ShinyModel;
import jakarta.annotation.Nonnull;

public record ShinyContainer(
		@Nonnull UUID id,
		List<ShinyModel> content) implements ShinyBlock {

	public ShinyContainer {
		Assertion.check().isNotNull(id);
	}
}
