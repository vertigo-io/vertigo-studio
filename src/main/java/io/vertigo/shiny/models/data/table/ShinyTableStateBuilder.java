package io.vertigo.shiny.models.data.table;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.models.ShinyProp;
import io.vertigo.shiny.models.ShinyState;

public final class ShinyTableStateBuilder implements Builder<ShinyState> {
	private boolean _sortable = false;
	private int _sortColumn = -1;
	private String _sortDirection = "asc"; // "asc" or "desc"
	private int _page = -1; // "
	private int _pageCount;

	public ShinyTableStateBuilder withSortColumn(final int sortColumn) {
		this._sortable = true;
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

	public ShinyState build() {
		return new ShinyState(List.of(
				ShinyProp.of("sortable", _sortable),
				ShinyProp.of("sortColumn", _sortColumn),
				ShinyProp.of("sortDirection", _sortDirection),
				ShinyProp.of("page", _page),
				ShinyProp.of("pageCount", _pageCount)));
	}
}
