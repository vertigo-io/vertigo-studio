package io.vertigo.shiny.models.data.board;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

/**
 * Builder for creating instances of ShinyBoardList.
 * A ShinyBoardList represents a list within a Shiny board, containing multiple ShinyBoardCard objects.
 */
public final class ShinyBoardListBuilder implements Builder<ShinyBoardList> {
	private UUID _id;
	private String _name;
	private int _position;
	private String _color;
	private final List<ShinyBoardCard> _cards = new ArrayList<>();

	/**
	 * Sets the unique identifier for the ShinyBoardList.
	 * If not set, a random UUID will be generated upon building.
	 * @param id The UUID for the list.
	 * @return This builder instance.
	 */
	public ShinyBoardListBuilder withId(@Nonnull final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	/**
	 * Sets the name or title of the ShinyBoardList.
	 * @param name The name of the list.
	 * @return This builder instance.
	 */
	public ShinyBoardListBuilder withName(@Nonnull final String name) {
		Assertion.check().isNotBlank(name);
		//---
		_name = name;
		return this;
	}

	/**
	 * Sets the position of the ShinyBoardList within the board.
	 * @param position The position of the list.
	 * @return This builder instance.
	 */
	public ShinyBoardListBuilder withPosition(final int position) {
		_position = position;
		return this;
	}

	/**
	 * Sets the color associated with the ShinyBoardList.
	 * @param color The color of the list.
	 * @return This builder instance.
	 */
	public ShinyBoardListBuilder withColor(@Nonnull final String color) {
		Assertion.check().isNotBlank(color);
		//---
		_color = color;
		return this;
	}

	/**
	 * Adds a single ShinyBoardCard to this list.
	 * @param card The ShinyBoardCard to add.
	 * @return This builder instance.
	 */
	public ShinyBoardListBuilder addCard(@Nonnull final ShinyBoardCard card) {
		Assertion.check().isNotNull(card);
		//---
		_cards.add(card);
		return this;
	}

	/**
	 * Adds multiple ShinyBoardCard objects to this list.
	 * @param cards The list of ShinyBoardCard objects to add.
	 * @return This builder instance.
	 */
	public ShinyBoardListBuilder addAllCards(@Nonnull final List<ShinyBoardCard> cards) {
		Assertion.check().isNotNull(cards);
		//---
		_cards.addAll(cards);
		return this;
	}

	/**
	 * Builds a new ShinyBoardList instance using the configured properties.
	 * If no ID was set, a random UUID is generated.
	 * @return A new ShinyBoardList instance.
	 */
	@Override
	public ShinyBoardList build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyBoardList(_id, _name, _position, _color, _cards);
	}
}
