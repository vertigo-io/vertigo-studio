package io.vertigo.shiny.components.input.multiselection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyMultiSelectionBuilder implements Builder<ShinyMultiSelection> {
	private String multiselectionTitle;
	private final List<String> multiselectionOptions = new ArrayList<>();
	private final Set<Integer> selectedIndices = new HashSet<>();

	// No public constructor, use ShinyMultiSelection.builder()
	ShinyMultiSelectionBuilder() {
		// Package-private constructor
	}

	public ShinyMultiSelectionBuilder withTitle(final String title) {
		this.multiselectionTitle = title;
		return this;
	}

	public ShinyMultiSelectionBuilder withOptions(final List<String> options) {
		Assertion.check().isNotNull(options);
		this.multiselectionOptions.clear();
		this.multiselectionOptions.addAll(options);
		return this;
	}

	public ShinyMultiSelectionBuilder withOptions(final String... options) {
		Assertion.check().isNotNull(options);
		return withOptions(Arrays.asList(options));
	}

	public ShinyMultiSelectionBuilder withSelected(final List<String> initialSelected) {
		Assertion.check().isNotNull(initialSelected);
		this.selectedIndices.clear();
		for (final String selectedOption : initialSelected) {
			final int index = multiselectionOptions.indexOf(selectedOption);
			if (index != -1) {
				selectedIndices.add(index);
			}
		}
		return this;
	}

	@Override
	public ShinyMultiSelection build() {
		// Perform any final validations here before building the object
		//---
		return new ShinyMultiSelection(multiselectionTitle, multiselectionOptions, selectedIndices);
	}
}
