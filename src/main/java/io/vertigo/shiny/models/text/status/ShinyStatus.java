package io.vertigo.shiny.models.text.status;

import java.util.List;

import io.vertigo.shiny.models.ShinyElement;

public record ShinyStatus(
		String title,
		List<ShinyStatusType> types) implements ShinyElement {

	public ShinyStatus {
	}
}
