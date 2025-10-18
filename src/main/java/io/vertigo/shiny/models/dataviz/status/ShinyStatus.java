package io.vertigo.shiny.models.dataviz.status;

import java.util.List;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyStatus(
		String title,
		List<ShinyStatusType> types) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyStatus";
	}

}
