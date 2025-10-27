package io.vertigo.shiny.models.data.table;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyTableStateBuilder implements Builder<ShinyTableState> {
	private UUID _id;
	private int _sortColumn = -1;
	private String _sortDirection = "asc"; // "asc" or "desc"
	private int _page = -1; // "
	private int _pageCount;

	public ShinyTableStateBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		this._id = id;
		return this;
	}

	public ShinyTableStateBuilder withSortColumn(final int sortColumn) {
		this._sortColumn = sortColumn;
		return this;
	}

	public ShinyTableStateBuilder withSortDirection(final String sortDirection) {
		Assertion.check().isNotBlank(sortDirection);
		//---
		this._sortDirection = sortDirection;
		return this;
	}

	public ShinyTableStateBuilder withPage(final int page) {
		_page = page;
		return this;
	}

	public ShinyTableStateBuilder withPageCount(int pageCount) {
		_pageCount = pageCount;
		return this;
	}

	public ShinyTableState build() {
		return new ShinyTableState(
				_id,
				_sortColumn,
				_sortDirection,
				_page,
				_pageCount);
	}
}
