package io.vertigo.shiny.models.data.board;

import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

/**
 * Builder for creating instances of ShinyBoardCard.
 * A ShinyBoardCard represents a single card within a Shiny board list.
 */
public final class ShinyBoardCardBuilder implements Builder<ShinyBoardCard> {
	private UUID _id;
	private String _title;
	private String _description;

	/**
	 * Sets the unique identifier for the ShinyBoardCard.
	 * If not set, a random UUID will be generated upon building.
	 * @param id The UUID for the card.
	 * @return This builder instance.
	 */
	public ShinyBoardCardBuilder withId(@Nonnull final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	/**
	 * Sets the title of the ShinyBoardCard.
	 * @param title The title of the card.
	 * @return This builder instance.
	 */
	public ShinyBoardCardBuilder withTitle(@Nonnull final String title) {
		Assertion.check().isNotBlank(title);
		//---
		_title = title;
		return this;
	}

	/**
	 * Sets a detailed description for the ShinyBoardCard.
	 * @param description The description of the card.
	 * @return This builder instance.
	 */
	public ShinyBoardCardBuilder withDescription(@Nonnull final String description) {
		Assertion.check().isNotBlank(description);
		//---
		_description = description;
		return this;
	}

	/**
	 * Builds a new ShinyBoardCard instance using the configured properties.
	 * If no ID was set, a random UUID is generated.
	 * @return A new ShinyBoardCard instance.
	 */
	@Override
	public ShinyBoardCard build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyBoardCard(_id, _title, _description);
	}
}
