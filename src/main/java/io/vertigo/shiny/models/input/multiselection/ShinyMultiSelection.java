package io.vertigo.shiny.models.input.multiselection;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyMultiSelection(
		String title,
		List<String> options,
		Set<Integer> selectedIndices) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyMultiSelection";
	}

	public List<String> getSelectedOptions() {
		return selectedIndices.stream()
				.sorted()
				.map(options::get)
				.collect(Collectors.toList());
	}
}
