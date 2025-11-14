package io.vertigo.shiny.models.input.form;

import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyForm(UUID id, String title, List<ShinyFormSection> sections) implements ShinyBlock {
	public ShinyForm {
		Assertion.check()
				.isNotNull(id)
				.isNotBlank(title)
				.isNotNull(sections);
	}
}
