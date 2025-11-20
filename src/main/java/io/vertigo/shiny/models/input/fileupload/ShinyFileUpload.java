package io.vertigo.shiny.models.input.fileupload;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import jakarta.annotation.Nonnull;

public record ShinyFileUpload(@Nonnull String label, boolean multiple, String accept) implements ShinyBlock {
	public ShinyFileUpload {
		Assertion.check()
				.isNotBlank(label);
	}
}
