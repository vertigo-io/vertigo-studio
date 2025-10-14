package io.vertigo.shiny.components.data.list;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyList(
		String title,
		List<Object> items, //String || ShinyList
		ShinyListType listType) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinyList";
	}

	public ShinyList {
		Assertion.check()
				.isNotNull(items)
				.isNotNull(listType);
	}
}
