package io.vertigo.shiny.models.data.board;

import java.util.List;
import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

/**
 * ShinyBoardList is a record representing a list component within a Shiny board.
 * It contains a collection of `ShinyBoardCard` objects and defines properties
 * for the list itself.
 *
 * @param id The unique identifier for this list.
 * @param name The name or title of the list.
 * @param position The position of the list within the board.
 * @param color The color associated with the list.
 * @param cards The list of `ShinyBoardCard` objects contained within this list.
 */
public record ShinyBoardList(
		UUID id,
		String name,
		int position,
		String color,
		List<ShinyBoardCard> cards) implements ShinyModel {

	/**
	 * Returns the type of this Shiny component, which is "ShinyBoardList".
	 * @return The string "ShinyBoardList".
	 */
	@Override
	public String shinyType() {
		return "ShinyBoardList";
	}
}
