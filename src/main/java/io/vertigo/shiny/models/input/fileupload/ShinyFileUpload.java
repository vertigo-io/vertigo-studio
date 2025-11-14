package io.vertigo.shiny.models.input.fileupload;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyFileUpload(String label, boolean multiple, String accept) implements ShinyBlock {
	public ShinyFileUpload {
		Assertion.check()
				.isNotBlank(label);
	}
}
