package io.vertigo.shiny.models.data.board;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyBoardBuilder implements Builder<ShinyBoard> {
	private UUID _id;
	private String _name;
	private String _description;
	private String _backgroundColor;
	private final List<ShinyBoardList> _lists = new ArrayList<>();

	public ShinyBoardBuilder withId(@Nonnull final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyBoardBuilder withName(@Nonnull final String name) {
		Assertion.check().isNotBlank(name);
		//---
		_name = name;
		return this;
	}

	public ShinyBoardBuilder withDescription(@Nonnull final String description) {
		Assertion.check().isNotBlank(description);
		//---
		_description = description;
		return this;
	}

	public ShinyBoardBuilder withBackgroundColor(@Nonnull final String backgroundColor) {
		Assertion.check().isNotBlank(backgroundColor);
		//---
		_backgroundColor = backgroundColor;
		return this;
	}

	public ShinyBoardBuilder addList(@Nonnull final ShinyBoardList list) {
		Assertion.check().isNotNull(list);
		//---
		_lists.add(list);
		return this;
	}

	public ShinyBoardBuilder addAllLists(@Nonnull final List<ShinyBoardList> lists) {
		Assertion.check().isNotNull(lists);
		//---
		_lists.addAll(lists);
		return this;
	}

	@Override
	public ShinyBoard build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyBoard(_id, _name, _description, _backgroundColor, _lists);
	}
}
