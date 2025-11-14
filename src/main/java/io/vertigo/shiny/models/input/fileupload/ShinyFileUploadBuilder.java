package io.vertigo.shiny.models.input.fileupload;

import io.vertigo.core.lang.Assertion;

public final class ShinyFileUploadBuilder {
	private String _label;
	private boolean _isMultiple;
	private String _accept;

	public ShinyFileUploadBuilder withLabel(final String label) {
		Assertion.check().isNotBlank(label);
		//---
		_label = label;
		return this;
	}

	public ShinyFileUploadBuilder isMultiple() {
		_isMultiple = true;
		return this;
	}

	public ShinyFileUploadBuilder withAccept(final String accept) {
		_accept = accept;
		return this;
	}

	public ShinyFileUpload build() {
		return new ShinyFileUpload(_label, _isMultiple, _accept);
	}
}
