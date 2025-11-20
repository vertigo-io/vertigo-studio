package io.vertigo.shiny.models.feedback.notification;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyNotification(@Nonnull String message, @Nonnull ShinyNotificationType type, int timeout) implements ShinyBlock {
	public ShinyNotification {
		Assertion.check()
				.isNotBlank(message)
				.isNotNull(type)
				.isTrue(timeout > 0, "Timeout must be greater than 0");
	}
}
