package io.vertigo.shiny.models;

import java.util.List;
import java.util.Optional;

public record ShinyState(List<ShinyProp> props) {

	public Optional<String> getValue(String key) {
		final Optional<ShinyProp> prop = props()
				.stream()
				.filter(p -> key.equals(p.key()))
				.findFirst();
		return prop.isPresent()
				? Optional.of(prop.get().value())
				: Optional.empty();
	}
}
