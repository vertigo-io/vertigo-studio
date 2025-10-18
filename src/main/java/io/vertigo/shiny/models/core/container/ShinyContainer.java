package io.vertigo.shiny.models.core.container;

import java.util.List;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyContainer(
		List<ShinyModel> components) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyContainer";
	}

}
