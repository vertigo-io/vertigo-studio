package io.vertigo.shiny.models.data.datagrid;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyDataGrid(String title, @Nonnull List<ShinyDataGridColumn> columns, @Nonnull List<Map<String, Object>> data) implements ShinyBlock {
	public ShinyDataGrid {
		Assertion.check()
				.isNotNull(columns)
				.isNotNull(data);
	}
}
