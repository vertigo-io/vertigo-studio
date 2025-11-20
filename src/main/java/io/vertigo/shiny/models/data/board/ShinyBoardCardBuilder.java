package io.vertigo.shiny.models.data.board;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import jakarta.annotation.Nonnull;

public final class ShinyBoardCardBuilder implements Builder<ShinyBoardCard> {
	private UUID _id;
	private String _title;
	private String _description;

	public ShinyBoardCardBuilder withId(@Nonnull final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyBoardCardBuilder withTitle(@Nonnull final String title) {
		Assertion.check().isNotBlank(title);
		//---
		_title = title;
		return this;
	}

	public ShinyBoardCardBuilder withDescription(@Nonnull final String description) {
		Assertion.check().isNotBlank(description);
		//---
		_description = description;
		return this;
	}

	@Override
	public ShinyBoardCard build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyBoardCard(_id, _title, _description);
	}
}
