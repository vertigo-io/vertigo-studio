package io.vertigo.shiny.components.data.list;

import java.util.List;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyList(
		String title,
		List<Object> items, //String || ShinyList
		ShinyListType type) implements ShinyComponent {
}
