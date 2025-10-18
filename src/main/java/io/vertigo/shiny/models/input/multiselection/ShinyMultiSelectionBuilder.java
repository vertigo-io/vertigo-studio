package io.vertigo.shiny.models.input.multiselection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyMultiSelectionBuilder implements Builder<ShinyMultiSelection> {
	private String _title;
	private final List<String> _options = new ArrayList<>();
	private final Set<Integer> _selectedIndices = new HashSet<>();

	public ShinyMultiSelectionBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyMultiSelectionBuilder withOptions(final List<String> options) {
		Assertion.check().isNotNull(options);
		this._options.clear();
		this._options.addAll(options);
		return this;
	}

	public ShinyMultiSelectionBuilder withOptions(final String... options) {
		Assertion.check().isNotNull(options);
		return withOptions(Arrays.asList(options));
	}

	public ShinyMultiSelectionBuilder withSelected(final List<String> initialSelected) {
		Assertion.check().isNotNull(initialSelected);
		this._selectedIndices.clear();
		for (final String selectedOption : initialSelected) {
			final int index = _options.indexOf(selectedOption);
			if (index != -1) {
				_selectedIndices.add(index);
			}
		}
		return this;
	}

	@Override
	public ShinyMultiSelection build() {
		// Perform any final validations here before building the object
		//---
		return new ShinyMultiSelection(_title, _options, _selectedIndices);
	}
}
