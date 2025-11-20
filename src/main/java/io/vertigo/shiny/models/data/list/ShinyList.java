package io.vertigo.shiny.models.data.list;

import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import jakarta.annotation.Nonnull;

public record ShinyList(
		@Nonnull UUID id,
		String title,
		@Nonnull List<Object> items, //String || ShinyList
		@Nonnull ShinyListType listType) implements ShinyBlock {

	public ShinyList {
		Assertion.check().isNotNull(id);
		Assertion.check()
				.isNotNull(items)
				.isNotNull(listType);
	}
}
