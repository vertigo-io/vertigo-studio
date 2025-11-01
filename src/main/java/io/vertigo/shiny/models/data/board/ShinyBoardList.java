package io.vertigo.shiny.models.data.board;

import java.util.List;
import java.util.UUID;

import io.vertigo.shiny.models.ShinyElement;

public record ShinyBoardList(
		UUID id,
		String name,
		int position,
		String color,
		List<ShinyBoardCard> cards) implements ShinyElement {

	@Override
	public String shinyType() {
		return "ShinyBoardList";
	}
}
