package io.vertigo.shiny.components.data.list;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyList(
		String title,
		List<Object> items,
		ShinyListType type,
		@JsonIgnore ShinyListStyle style) implements ShinyComponent {
}
