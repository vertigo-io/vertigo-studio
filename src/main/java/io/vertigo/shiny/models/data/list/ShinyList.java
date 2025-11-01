package io.vertigo.shiny.models.data.list;

import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyList(
		UUID id,
		String title,
		List<Object> items, //String || ShinyList
		ShinyListType listType) implements ShinyBlock {

	public ShinyList {
		Assertion.check().isNotNull(id);
		Assertion.check()
				.isNotNull(items)
				.isNotNull(listType);
	}
}
