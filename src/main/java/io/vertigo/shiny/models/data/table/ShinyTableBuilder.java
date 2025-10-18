package io.vertigo.shiny.models.data.table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.models.ShinyProp;

public final class ShinyTableBuilder implements Builder<ShinyTable> {
	private UUID _id;
	private String _title;
	private String _noDataFound;
	private String[] _header;
	private final List<String[]> _rows = new ArrayList<>();
	private boolean _sortable;
	private int _sortColumn = -1;
	private String _sortDirection = "asc"; // "asc" or "desc"
	private List<ShinyProp> _props = null;//par défaut pas de pagination

	public ShinyTableBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		this._id = id;
		return this;
	}

	public ShinyTableBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		this._title = title;
		return this;
	}

	public ShinyTableBuilder withNoDataFound(final String noDataFound) {
		Assertion.check().isNotBlank(noDataFound);
		//---
		this._noDataFound = noDataFound;
		return this;
	}

	public ShinyTableBuilder withHeader(final String... header) {
		Assertion.check().isNotNull(header);
		//---
		this._header = header;
		return this;
	}

	public ShinyTableBuilder addRow(final String... row) {
		Assertion.check().isNotNull(row);
		//---
		this._rows.add(row);
		return this;
	}

	public ShinyTableBuilder addAllRows(final List<String[]> rows) {
		Assertion.check().isNotNull(rows);
		//---
		this._rows.addAll(rows);
		return this;
	}

	public ShinyTableBuilder withSortable(final boolean sortable) {
		this._sortable = sortable;
		return this;
	}

	public ShinyTableBuilder withSortColumn(final int sortColumn) {
		this._sortColumn = sortColumn;
		return this;
	}

	public ShinyTableBuilder withSortDirection(final String sortDirection) {
		Assertion.check().isNotBlank(sortDirection);
		//---
		this._sortDirection = sortDirection;
		return this;
	}

	public ShinyTableBuilder withPage(final int page, int pageCount) {
		_props = List.of(
				ShinyProp.of("page", page),
				ShinyProp.of("pageCount", pageCount));
		return this;
	}

	public ShinyTable build() {
		_id = _id == null
				? UUID.randomUUID()
				: _id;
		return new ShinyTable(_id, _title, _noDataFound, _header, _rows.toArray(String[][]::new), _sortable, _sortColumn, _sortDirection, _props);
	}
}
