package io.vertigo.shiny.models.data.table;

import io.vertigo.shiny.models.ShinyState;

public record ShinyTableState(
		int sortColumn, //-1 => no sort
		String sortDirection,
		int page,
		//		int rpp, 
		int pageCount) implements ShinyState {

}
