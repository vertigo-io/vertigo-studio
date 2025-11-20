package io.vertigo.shiny.models.layout.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.models.ShinyBlock;
import jakarta.annotation.Nonnull;

public final class ShinyGridBuilder implements Builder<ShinyGrid> {
	private UUID _id;
	private int _columns = 1; // Default to 1 column
	private final List<ShinyBlock> _content = new ArrayList<>();

	public ShinyGridBuilder withId(@Nonnull final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyGridBuilder withColumns(final int columns) {
		Assertion.check().isTrue(columns > 0, "Columns must be a positive number");
		//---
		this._columns = columns;
		return this;
	}

	public ShinyGridBuilder addBlock(@Nonnull final ShinyBlock block) {
		Assertion.check().isNotNull(block);
		//---
		this._content.add(block);
		return this;
	}

	public ShinyGridBuilder addAllBlocks(@Nonnull final List<ShinyBlock> blocks) {
		Assertion.check().isNotNull(blocks);
		//---
		this._content.addAll(blocks);
		return this;
	}

	public ShinyGridBuilder addAllBlocks(@Nonnull final ShinyBlock... blocks) {
		Assertion.check().isNotNull(blocks);
		//---
		return addAllBlocks(List.of(blocks));
	}

	@Override
	public ShinyGrid build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyGrid(_id, _columns, _content);
	}
}
