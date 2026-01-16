package io.vertigo.shiny.models.data.board;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

/**
 * ShinyBoardCard is a record representing a card component that can be placed within a Shiny board list.
 * It extends ShinyModel and holds information about a single card.
 *
 * @param id The unique identifier for this card.
 * @param title The title of the card.
 * @param description A detailed description of the card's content.
 */
public record ShinyBoardCard(
		UUID id,
		String title,
		String description
) implements ShinyModel {

	/**
	 * Returns the type of this Shiny component, which is "ShinyBoardCard".
	 * @return The string "ShinyBoardCard".
	 */
	@Override
	public String shinyType() {
		return "ShinyBoardCard";
	}
}
