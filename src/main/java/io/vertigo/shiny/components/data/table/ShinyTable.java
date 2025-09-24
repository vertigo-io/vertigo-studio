package io.vertigo.shiny.components.data.table;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

/**
 * Represents a table that can be printed in a terminal with colors,
 * borders and formatted numeric values.
 */
public record ShinyTable(
		String title,
		String noDataFound,
		String[] header,
		List<String[]> rows) implements ShinyComponent {

	public ShinyTable {
		Assertion.check()
				.isNotNull(rows)
				.when(rows.isEmpty(), () -> Assertion.check().isNotBlank(noDataFound, "noDataFound message is required when table is empty"))
				.isNotNull(header, "Table header is required");
	}
}
