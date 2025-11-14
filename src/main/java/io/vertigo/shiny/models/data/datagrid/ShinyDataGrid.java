package io.vertigo.shiny.models.data.datagrid;

import java.util.List;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyDataGrid(String title, List<ShinyDataGridColumn> columns, List<Map<String, Object>> data) implements ShinyBlock {
	public ShinyDataGrid {
		Assertion.check()
				.isNotNull(columns)
				.isNotNull(data);
	}
}
