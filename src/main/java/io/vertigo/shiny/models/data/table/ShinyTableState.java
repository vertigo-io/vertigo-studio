package io.vertigo.shiny.models.data.table;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyState;

public record ShinyTableState(
		UUID id,
		int sortColumn, //-1 => no sort
		String sortDirection,
		int page,
		//		int rpp, 
		int pageCount) implements ShinyState {

}
