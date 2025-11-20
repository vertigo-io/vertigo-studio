package io.vertigo.shiny.models.block;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyContainer(
		@Nonnull UUID id,
		List<ShinyModel> content) implements ShinyBlock {

	public ShinyContainer {
		Assertion.check().isNotNull(id);
	}
}
