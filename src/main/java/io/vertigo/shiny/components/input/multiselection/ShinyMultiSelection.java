package io.vertigo.shiny.components.input.multiselection;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyMultiSelection(
		String title,
		List<String> options,
		Set<Integer> selectedIndices) implements ShinyComponent {

	public List<String> getSelectedOptions() {
		return selectedIndices.stream()
				.sorted()
				.map(options::get)
				.collect(Collectors.toList());
	}
}
