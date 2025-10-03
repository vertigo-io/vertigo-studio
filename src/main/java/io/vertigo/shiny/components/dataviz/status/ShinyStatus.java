package io.vertigo.shiny.components.dataviz.status;

import java.util.List;

import io.vertigo.shiny.components.ShinyComponent;

import io.vertigo.shiny.ShinyType;

@ShinyType("status")
public record ShinyStatus(
		String title,
		List<ShinyStatusType> types) implements ShinyComponent {
}
