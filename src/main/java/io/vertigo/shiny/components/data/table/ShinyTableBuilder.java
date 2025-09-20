package io.vertigo.shiny.components.data.table;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinyTableBuilder implements Builder<ShinyTable> {
	private String tableTitle;
	private String noDataFound;
	private String[] tableHeader;
	private final List<String[]> tableRows = new ArrayList<>();
	private ShinyTableStyle tableStyle;

	public ShinyTableBuilder() {
		this.tableStyle = Shiny.theme().tableStyle(); // Initialize default style
	}

	public ShinyTableBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		this.tableTitle = title;
		return this;
	}

	public ShinyTableBuilder withStyle(ShinyTableStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.tableStyle = style;
		return this;
	}

	public ShinyTableBuilder withNoDataFound(final String message) {
		this.noDataFound = message;
		return this;
	}

	public ShinyTableBuilder withHeader(final String... header) {
		this.tableHeader = header;
		return this;
	}

	public ShinyTableBuilder addRow(final String... row) {
		this.tableRows.add(row);
		return this;
	}

	public ShinyTableBuilder addAllRows(final List<String[]> rows) {
		this.tableRows.addAll(rows);
		return this;
	}

	public ShinyTableBuilder addAllRows(final String[]... rows) {
		this.tableRows.addAll(List.of(rows));
		return this;
	}

	@Override
	public ShinyTable build() {
		return new ShinyTable(
				tableTitle, noDataFound, tableHeader, tableRows, tableStyle);
	}
}
