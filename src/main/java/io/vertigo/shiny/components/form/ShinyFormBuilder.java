package io.vertigo.shiny.components.form;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyFormBuilder implements Builder<ShinyForm> {
	private String _title;
	private final List<ShinyFormSection> sections = new ArrayList<>();

	public ShinyFormBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyFormBuilder addSection(final String sectionTitle, final List<ShinyFormField> fields) {
		Assertion.check().isNotBlank(sectionTitle).isNotNull(fields);
		this.sections.add(new ShinyFormSection(sectionTitle, fields));
		return this;
	}

	public ShinyForm build() {
		return new ShinyForm(_title, sections);
	}
}
