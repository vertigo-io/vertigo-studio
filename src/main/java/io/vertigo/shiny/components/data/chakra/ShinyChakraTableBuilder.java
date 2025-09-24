package io.vertigo.shiny.components.data.chakra;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;

public final class ShinyChakraTableBuilder {
	private String myId;
	private String myTitle;
	private String myNoDataFound;
	private String[] myHeader;
	private final List<String[]> myRows = new ArrayList<>();
	private boolean mySortable;
	private int mySortColumn = -1;
	private String mySortDirection = "asc"; // "asc" or "desc"

	public ShinyChakraTableBuilder() {
		myId = UUID.randomUUID().toString();
	}

	public ShinyChakraTableBuilder withId(final String id) {
		Assertion.check().isNotBlank(id);
		//---
		myId = id;
		return this;
	}

	public ShinyChakraTableBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		myTitle = title;
		return this;
	}

	public ShinyChakraTableBuilder withNoDataFound(final String noDataFound) {
		Assertion.check().isNotBlank(noDataFound);
		//---
		myNoDataFound = noDataFound;
		return this;
	}

	public ShinyChakraTableBuilder withHeader(final String... header) {
		Assertion.check().isNotNull(header);
		//---
		myHeader = header;
		return this;
	}

	public ShinyChakraTableBuilder addRow(final String... row) {
		Assertion.check().isNotNull(row);
		//---
		myRows.add(row);
		return this;
	}

	public ShinyChakraTableBuilder addAllRows(final List<String[]> rows) {
		Assertion.check().isNotNull(rows);
		//---
		myRows.addAll(rows);
		return this;
	}

	public ShinyChakraTableBuilder withSortable(final boolean sortable) {
		mySortable = sortable;
		return this;
	}

	public ShinyChakraTableBuilder withSortColumn(final int sortColumn) {
		mySortColumn = sortColumn;
		return this;
	}

	public ShinyChakraTableBuilder withSortDirection(final String sortDirection) {
		Assertion.check().isNotBlank(sortDirection);
		//---
		mySortDirection = sortDirection;
		return this;
	}

	public ShinyChakraTable build() {
		return new ShinyChakraTable(myId, myTitle, myNoDataFound, myHeader, myRows.toArray(String[][]::new), mySortable, mySortColumn, mySortDirection);
	}
}