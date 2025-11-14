package io.vertigo.shiny.models.data.datagrid;

import io.vertigo.core.lang.Assertion;

public record ShinyDataGridColumn(String header, String field, boolean sortable, boolean filterable) {
	public ShinyDataGridColumn {
		Assertion.check()
				.isNotBlank(header)
				.isNotBlank(field);
	}
}
