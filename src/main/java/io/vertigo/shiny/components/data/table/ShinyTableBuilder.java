package io.vertigo.shiny.components.data.table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyTableBuilder implements Builder<ShinyTable> {
	private String myId;
	private String myTitle;
	private String myNoDataFound;
	private String[] myHeader;
	private final List<String[]> myRows = new ArrayList<>();
	private boolean mySortable;
	private int mySortColumn = -1;
	private String mySortDirection = "asc"; // "asc" or "desc"

	public ShinyTableBuilder() {
		myId = UUID.randomUUID().toString();
	}

	public ShinyTableBuilder withId(final String id) {
		Assertion.check().isNotBlank(id);
		//---
		myId = id;
		return this;
	}

	public ShinyTableBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		myTitle = title;
		return this;
	}

	public ShinyTableBuilder withNoDataFound(final String noDataFound) {
		Assertion.check().isNotBlank(noDataFound);
		//---
		myNoDataFound = noDataFound;
		return this;
	}

	public ShinyTableBuilder withHeader(final String... header) {
		Assertion.check().isNotNull(header);
		//---
		myHeader = header;
		return this;
	}

	public ShinyTableBuilder addRow(final String... row) {
		Assertion.check().isNotNull(row);
		//---
		myRows.add(row);
		return this;
	}

	public ShinyTableBuilder addAllRows(final List<String[]> rows) {
		Assertion.check().isNotNull(rows);
		//---
		myRows.addAll(rows);
		return this;
	}

	public ShinyTableBuilder withSortable(final boolean sortable) {
		mySortable = sortable;
		return this;
	}

	public ShinyTableBuilder withSortColumn(final int sortColumn) {
		mySortColumn = sortColumn;
		return this;
	}

	public ShinyTableBuilder withSortDirection(final String sortDirection) {
		Assertion.check().isNotBlank(sortDirection);
		//---
		mySortDirection = sortDirection;
		return this;
	}

	public ShinyTable build() {
		return new ShinyTable(myId, myTitle, myNoDataFound, myHeader, myRows.toArray(String[][]::new), mySortable, mySortColumn, mySortDirection);
	}
}
