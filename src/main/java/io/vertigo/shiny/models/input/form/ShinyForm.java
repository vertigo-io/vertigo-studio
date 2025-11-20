package io.vertigo.shiny.models.input.form;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyForm(@Nonnull UUID id, @Nonnull String title, @Nonnull List<ShinyFormSection> sections) implements ShinyBlock {
	public ShinyForm {
		Assertion.check()
				.isNotNull(id)
				.isNotBlank(title)
				.isNotNull(sections);
	}
}
