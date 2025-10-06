package io.vertigo.shiny.components.data.list;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyType;
import io.vertigo.shiny.components.ShinyComponent;

@ShinyType("list")
public record ShinyList(
		String title,
		List<Object> items, //String || ShinyList
		ShinyListType type) implements ShinyComponent {

	public ShinyList {
		Assertion.check()
				.isNotNull(items)
				.isNotNull(type);
	}
}
