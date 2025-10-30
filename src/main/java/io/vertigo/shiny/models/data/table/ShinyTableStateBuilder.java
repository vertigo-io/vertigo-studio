package io.vertigo.shiny.models.data.table;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyTableStateBuilder implements Builder<ShinyTableState> {
	private int _sortColumn = -1;
	private String _sortDirection = "asc"; // "asc" or "desc"
	private int _page = -1; // "
	private int _pageCount;

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
				_sortColumn,
				_sortDirection,
				_page,
				_pageCount);
	}
}
