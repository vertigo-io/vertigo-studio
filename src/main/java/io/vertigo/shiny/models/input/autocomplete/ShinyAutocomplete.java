package io.vertigo.shiny.models.input.autocomplete;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyAutocomplete(String label, List<String> options, String value, String placeholder) implements ShinyBlock {
	public ShinyAutocomplete {
		Assertion.check()
				.isNotBlank(label)
				.isNotNull(options);
	}
}
