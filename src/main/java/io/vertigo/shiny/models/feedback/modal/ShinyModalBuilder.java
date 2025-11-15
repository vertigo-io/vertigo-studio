package io.vertigo.shiny.models.feedback.modal;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public final class ShinyModalBuilder {
	private String _title;
	private ShinyBlock _content;

	public ShinyModalBuilder withContent(final ShinyBlock content) {
	private boolean _isPersistent;

	public ShinyModalBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		_title = title;
		return this;
	}

	public ShinyModalBuilder withContent(final ShinyModel content) {
		Assertion.check().isNotNull(content);
		//---
		_content = content;
		return this;
	}

	public ShinyModalBuilder isPersistent() {
		_isPersistent = true;
		return this;
	}

	public ShinyModal build() {
		return new ShinyModal(_title, _content, _isPersistent);
	}
}
