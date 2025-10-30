package io.vertigo.shiny.models.data.table;

import java.util.List;
import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.data.table.cell.ShinyTableCell;

public record ShinyTable(
		UUID id,
		String title,
		String noDataFound,
		String[] header,
		List<List<ShinyTableCell>> rows,
		ShinyTableState state) implements ShinyModel {
}
