package io.vertigo.shiny.models.feedback.modal;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import jakarta.annotation.Nonnull;

public record ShinyModal(@Nonnull String title, @Nonnull ShinyBlock content, boolean persistent) implements ShinyBlock {
	public ShinyModal {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(content);
	}
}
