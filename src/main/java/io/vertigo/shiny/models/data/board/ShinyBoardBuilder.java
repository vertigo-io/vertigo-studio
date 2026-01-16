package io.vertigo.shiny.models.data.board;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

/**
 * Builder for creating instances of ShinyBoard.
 * A ShinyBoard is used to display a collection of lists, each containing cards.
 */
public final class ShinyBoardBuilder implements Builder<ShinyBoard> {
	private UUID _id;
	private String _name;
	private String _description;
	private String _backgroundColor;
	private final List<ShinyBoardList> _lists = new ArrayList<>();

	/**
	 * Sets the unique identifier for the ShinyBoard.
	 * If not set, a random UUID will be generated upon building.
	 * @param id The UUID for the board.
	 * @return This builder instance.
	 */
	public ShinyBoardBuilder withId(@Nonnull final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	/**
	 * Sets the name or title of the ShinyBoard.
	 * @param name The name of the board.
	 * @return This builder instance.
	 */
	public ShinyBoardBuilder withName(@Nonnull final String name) {
		Assertion.check().isNotBlank(name);
		//---
		_name = name;
		return this;
	}

	/**
	 * Sets a brief description for the ShinyBoard.
	 * @param description The description of the board.
	 * @return This builder instance.
	 */
	public ShinyBoardBuilder withDescription(@Nonnull final String description) {
		Assertion.check().isNotBlank(description);
		//---
		_description = description;
		return this;
	}

	/**
	 * Sets the background color of the ShinyBoard.
	 * @param backgroundColor The background color of the board.
	 * @return This builder instance.
	 */
	public ShinyBoardBuilder withBackgroundColor(@Nonnull final String backgroundColor) {
		Assertion.check().isNotBlank(backgroundColor);
		//---
		_backgroundColor = backgroundColor;
		return this;
	}

	/**
	 * Adds a single ShinyBoardList to the board.
	 * @param list The ShinyBoardList to add.
	 * @return This builder instance.
	 */
	public ShinyBoardBuilder addList(@Nonnull final ShinyBoardList list) {
		Assertion.check().isNotNull(list);
		//---
		_lists.add(list);
		return this;
	}

	/**
	 * Adds multiple ShinyBoardList objects to the board.
	 * @param lists The list of ShinyBoardList objects to add.
	 * @return This builder instance.
	 */
	public ShinyBoardBuilder addAllLists(@Nonnull final List<ShinyBoardList> lists) {
		Assertion.check().isNotNull(lists);
		//---
		_lists.addAll(lists);
		return this;
	}

	/**
	 * Builds a new ShinyBoard instance using the configured properties.
	 * If no ID was set, a random UUID is generated.
	 * @return A new ShinyBoard instance.
	 */
	@Override
	public ShinyBoard build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyBoard(_id, _name, _description, _backgroundColor, _lists);
	}
}
