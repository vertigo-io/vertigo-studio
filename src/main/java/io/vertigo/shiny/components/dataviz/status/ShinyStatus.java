package io.vertigo.shiny.components.dataviz.status;

import java.util.List;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyStatus(
		String title,
		List<ShinyStatusType> types) implements ShinyComponent {

	@Override
	public String type() {
		return "status";
	}

}
