package io.vertigo.shiny.models.data.board;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyBoardListBuilder implements Builder<ShinyBoardList> {
	private UUID _id;
	private String _name;
	private int _position;
	private final List<ShinyBoardCard> _cards = new ArrayList<>();

	public ShinyBoardListBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyBoardListBuilder withName(final String name) {
		Assertion.check().isNotBlank(name);
		//---
		_name = name;
		return this;
	}

	public ShinyBoardListBuilder withPosition(final int position) {
		_position = position;
		return this;
	}

	public ShinyBoardListBuilder addCard(final ShinyBoardCard card) {
		Assertion.check().isNotNull(card);
		//---
		_cards.add(card);
		return this;
	}

	public ShinyBoardListBuilder addAllCards(final List<ShinyBoardCard> cards) {
		Assertion.check().isNotNull(cards);
		//---
		_cards.addAll(cards);
		return this;
	}

	@Override
	public ShinyBoardList build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyBoardList(_id, _name, _position, _cards);
	}
}
