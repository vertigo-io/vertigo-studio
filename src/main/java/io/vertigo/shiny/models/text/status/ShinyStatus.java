package io.vertigo.shiny.models.text.status;

import java.util.List;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyStatus(
		String title,
		List<ShinyStatusType> types) implements ShinyModel {

	public ShinyStatus {
	}
}
