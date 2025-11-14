package io.vertigo.shiny.models.feedback.modal;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyModal(String title, ShinyModel content, boolean persistent) implements ShinyBlock {
	public ShinyModal {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(content);
	}
}
