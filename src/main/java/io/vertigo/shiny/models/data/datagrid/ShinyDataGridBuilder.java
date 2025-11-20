package io.vertigo.shiny.models.data.datagrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;

public final class ShinyDataGridBuilder {
	private String _title;
	private final List<ShinyDataGridColumn> _columns = new ArrayList<>();
	private List<Map<String, Object>> _data = new ArrayList<>();

	public ShinyDataGridBuilder withTitle(@Nonnull final String title) {
		Assertion.check().isNotBlank(title);
		//---
		_title = title;
		return this;
	}

	public ShinyDataGridBuilder addColumn(@Nonnull final String header, @Nonnull final String field) {
		return addColumn(header, field, false, false);
	}

	public ShinyDataGridBuilder addColumn(@Nonnull final String header, @Nonnull final String field, final boolean sortable, final boolean filterable) {
		Assertion.check()
				.isNotBlank(header)
				.isNotBlank(field);
		//---
		_columns.add(new ShinyDataGridColumn(header, field, sortable, filterable));
		return this;
	}

	public ShinyDataGridBuilder withData(@Nonnull final List<Map<String, Object>> data) {
		Assertion.check().isNotNull(data);
		//---
		_data = data;
		return this;
	}

	public ShinyDataGrid build() {
		return new ShinyDataGrid(_title, _columns, _data);
	}
}
