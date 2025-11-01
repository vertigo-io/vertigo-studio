package io.vertigo.shiny.models.data.board;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyElement;

public record ShinyBoardCard(
		UUID id,
		String title,
		String description
) implements ShinyElement {

	@Override
	public String shinyType() {
		return "ShinyBoardCard";
	}
}
