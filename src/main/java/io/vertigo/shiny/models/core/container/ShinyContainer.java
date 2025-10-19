package io.vertigo.shiny.models.core.container;

import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyContainer(
		UUID id,
		List<ShinyModel> components) implements ShinyModel {

	public ShinyContainer {
		Assertion.check().isNotNull(id);
	}
}
