package io.vertigo.shiny.models.feedback.alert;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;
import jakarta.annotation.Nonnull;

public record ShinyAlert(
		@Nonnull UUID id,
		@Nonnull ShinyAlertType alertType,
		String title,
		@Nonnull String content,
		boolean closable,
		String icon,
		boolean prominent) implements ShinyModel {

	public ShinyAlert {
		Assertion.check().isNotNull(id);
		Assertion.check()
				.isNotNull(alertType, "Type cannot be null")
				.isNotBlank(content, "Content cannot be blank");
	}
}
