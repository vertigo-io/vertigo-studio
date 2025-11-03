package io.vertigo.shiny.models.layout.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.models.ShinyModel;

public final class ShinyGridBuilder implements Builder<ShinyGrid> {
	private UUID _id;
	private int _columns = 1; // Default to 1 column
	private final List<ShinyModel> _content = new ArrayList<>();

	public ShinyGridBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyGridBuilder withColumns(final int columns) {
		Assertion.check().isTrue(columns > 0, "Columns must be a positive number");
		this._columns = columns;
		return this;
	}

	public ShinyGridBuilder addContent(final ShinyModel model) {
		Assertion.check().isNotNull(model);
		this._content.add(model);
		return this;
	}

	public ShinyGridBuilder addAllContent(final List<ShinyModel> models) {
		Assertion.check().isNotNull(models);
		this._content.addAll(models);
		return this;
	}

	public ShinyGridBuilder addAllContent(final ShinyModel... models) {
		Assertion.check().isNotNull(models);
		this._content.addAll(List.of(models));
		return this;
	}

	@Override
	public ShinyGrid build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyGrid(_id, _columns, _content);
	}
}
