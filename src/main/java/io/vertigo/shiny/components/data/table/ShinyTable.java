package io.vertigo.shiny.components.data.table;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyTable(
		String id,
		String title,
		String noDataFound,
		String[] header,
		String[][] rows,
		boolean sortable,
		int sortColumn,
		String sortDirection) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinyTable";
	}
}
