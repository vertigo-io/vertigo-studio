package io.vertigo.shiny.models;

import java.util.List;
import java.util.Optional;

/**
 * ShinyState is a record that represents the current state of a Shiny model.
 * It is composed of a list of `ShinyProp` instances, allowing for dynamic
 * state management of Shiny components.
 */
public record ShinyState(List<ShinyProp> props) {

	/**
	 * Retrieves the value of a specific property from the state.
	 * @param key The key of the property to retrieve.
	 * @return An Optional containing the string value of the property if found, otherwise an empty Optional.
	 */
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

