package io.vertigo.shiny.models.data.table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.models.ShinyState;
import io.vertigo.shiny.models.data.table.cell.ShinyStringCell;
import io.vertigo.shiny.models.data.table.cell.ShinyTableCell;

public final class ShinyTableBuilder implements Builder<ShinyTable> {
	private UUID _id;
	private String _title;
	private String _noDataFound;
	private String[] _header;
	private final List<List<ShinyTableCell>> _rows = new ArrayList<>();
	private ShinyState _state;

	public ShinyTableBuilder withId(@Nonnull final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		this._id = id;
		return this;
	}

	public ShinyTableBuilder withTitle(@Nonnull final String title) {
		Assertion.check().isNotBlank(title);
		//---
		this._title = title;
		return this;
	}

	public ShinyTableBuilder withNoDataFound(@Nonnull final String noDataFound) {
		Assertion.check().isNotBlank(noDataFound);
		//---
		this._noDataFound = noDataFound;
		return this;
	}

	public ShinyTableBuilder withHeader(@Nonnull final String... header) {
		Assertion.check().isNotNull(header);
		//---
		this._header = header;
		return this;
	}

	public ShinyTableBuilder addRow(@Nonnull final String... cols) {
		Assertion.check().isNotNull(cols);
		//---
		List<ShinyTableCell> row = new ArrayList<>();
		for (String col : cols) {
			row.add(new ShinyStringCell(UUID.randomUUID(), col));
		}
		this._rows.add(row);
		return this;
	}

	public ShinyTableBuilder addRow(@Nonnull final ShinyTableCell... row) {
		Assertion.check().isNotNull(row);
		//---
		this._rows.add(List.of(row));
		return this;
	}

	public ShinyTableBuilder addRow(@Nonnull final List<ShinyTableCell> row) {
		Assertion.check().isNotNull(row);
		//---
		this._rows.add(row);
		return this;
	}

	public ShinyTableBuilder addAllRows(@Nonnull final List<String[]> rows) {
		Assertion.check().isNotNull(rows);
		//---
		for (String[] row : rows) {
			this.addRow(row);
		}
		return this;
	}

	public ShinyTableBuilder addAllRowsOfCells(@Nonnull final List<List<ShinyTableCell>> rows) {
		Assertion.check().isNotNull(rows);
		//---
		this._rows.addAll(rows);
		return this;
	}

	public ShinyTableBuilder withState(@Nonnull ShinyState state) {
		Assertion.check().isNotNull(state);
		//---
		this._state = state;
		return this;
	}

	public ShinyTable build() {
		return new ShinyTable(
				_id,
				_title,
				_noDataFound,
				_header,
				_rows,
				_state);
	}
}
