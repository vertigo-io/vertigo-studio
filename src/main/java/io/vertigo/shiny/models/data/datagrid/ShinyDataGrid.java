package io.vertigo.shiny.models.data.datagrid;

import java.util.List;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import jakarta.annotation.Nonnull;

public record ShinyDataGrid(String title, @Nonnull List<ShinyDataGridColumn> columns, @Nonnull List<Map<String, Object>> data) implements ShinyBlock {
	public ShinyDataGrid {
		Assertion.check()
				.isNotNull(columns)
				.isNotNull(data);
	}
}
