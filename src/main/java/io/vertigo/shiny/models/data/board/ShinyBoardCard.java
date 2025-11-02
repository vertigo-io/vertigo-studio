package io.vertigo.shiny.models.data.board;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyBoardCard(
		UUID id,
		String title,
		String description
) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyBoardCard";
	}
}
