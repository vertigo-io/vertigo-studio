package io.vertigo.shiny.models.data.board;

import java.util.List;
import java.util.UUID;

import io.vertigo.shiny.models.ShinyBlock;

/**
 * ShinyBoard is a record representing a board component within the Shiny framework.
 * It is used to display a collection of lists, each containing cards, often used for task management or kanban-style interfaces.
 *
 * @param id The unique identifier for this board.
 * @param name The name or title of the board.
 * @param description A brief description of the board.
 * @param backgroundColor The background color of the board.
 * @param lists The list of `ShinyBoardList` objects that belong to this board.
 */
public record ShinyBoard(
		UUID id,
		String name,
		String description,
		String backgroundColor,
		List<ShinyBoardList> lists) implements ShinyBlock {

	/**
	 * Returns the type of this Shiny component, which is "ShinyBoard".
	 * @return The string "ShinyBoard".
	 */
	@Override
	public String shinyType() {
		return "ShinyBoard";
	}
}
