package io.vertigo.shiny.models.input.multiselection;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyMultiSelection(
		UUID id,
		String title,
		List<String> options,
		Set<Integer> selectedIndices) implements ShinyModel {

	public List<String> getSelectedOptions() {
		return selectedIndices.stream()
				.sorted()
				.map(options::get)
				.collect(Collectors.toList());
	}
}
