package io.vertigo.shiny.models.data.form;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyFormBuilder implements Builder<ShinyForm> {
	private UUID _id;
	private String _title;
	private final List<ShinyFormSection> _sections = new ArrayList<>();

	public ShinyFormBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyFormBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	    public ShinyFormBuilder addSection(final String sectionTitle, final List<ShinyFormField> fields) {
	        return addSection(sectionTitle, fields, false, false); // Default to not collapsible and not initially collapsed
	    }
	
	    public ShinyFormBuilder addSection(final String sectionTitle, final List<ShinyFormField> fields, final boolean collapsible, final boolean initiallyCollapsed) {
	        Assertion.check().isNotBlank(sectionTitle).isNotNull(fields);
	        this._sections.add(new ShinyFormSection(sectionTitle, fields, collapsible, initiallyCollapsed));
	        return this;
	    }
	public ShinyForm build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyForm(_id, _title, _sections);
	}
}
