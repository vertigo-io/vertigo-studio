package io.vertigo.shiny.models.data.form;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyForm(String title, List<ShinyFormSection> sections) implements ShinyModel {
	public ShinyForm {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(sections);
	}

	@Override
	public String shinyType() {
		return "ShinyForm";
	}
}
