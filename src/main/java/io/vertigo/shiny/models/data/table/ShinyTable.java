package io.vertigo.shiny.models.data.table;

import java.util.List;

import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.ShinyProp;

public record ShinyTable(
		String id,
		String title,
		String noDataFound,
		String[] header,
		String[][] rows,
		boolean sortable,
		int sortColumn,
		String sortDirection,
		List<ShinyProp> props) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyTable";
	}

}
