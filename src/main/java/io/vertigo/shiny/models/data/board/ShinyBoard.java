package io.vertigo.shiny.models.data.board;

import java.util.List;
import java.util.UUID;

import io.vertigo.shiny.models.ShinyBlock;

public record ShinyBoard(
		UUID id,
		String name,
		String description,
		String backgroundColor,
		List<ShinyBoardList> lists) implements ShinyBlock {

	@Override
	public String shinyType() {
		return "ShinyBoard";
	}
}
