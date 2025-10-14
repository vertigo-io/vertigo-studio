package io.vertigo.shiny.components.data.form;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyForm(String title, List<ShinyFormSection> sections) implements ShinyComponent {
	public ShinyForm {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(sections);
	}

	@Override
	public String type() {
		return "ShinyForm";
	}
}
