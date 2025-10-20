package io.vertigo.shiny.models.data.board;

import java.util.List;
import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyBoardList(
		UUID id,
		String name,
		int position,
		String color,
		List<ShinyBoardCard> cards) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyBoardList";
	}
}
