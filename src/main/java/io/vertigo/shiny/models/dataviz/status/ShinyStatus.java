package io.vertigo.shiny.models.dataviz.status;

import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyStatus(
		UUID id,
		String title,
		List<ShinyStatusType> types) implements ShinyModel {

	public ShinyStatus {
		Assertion.check().isNotNull(id);
	}

}
