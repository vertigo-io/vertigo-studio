package io.vertigo.shiny.models.feedback.notification;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;

public final class ShinyNotificationBuilder {
	private String _message;
	private ShinyNotificationType _type = ShinyNotificationType.INFO;
	private int _timeout = 5000; // Default 5 seconds

	public ShinyNotificationBuilder withMessage(@Nonnull final String message) {
		Assertion.check().isNotBlank(message);
		//---
		_message = message;
		return this;
	}

	public ShinyNotificationBuilder withType(@Nonnull final ShinyNotificationType type) {
		Assertion.check().isNotNull(type);
		//---
		_type = type;
		return this;
	}

	public ShinyNotificationBuilder withTimeout(final int timeout) {
		Assertion.check().isTrue(timeout > 0, "Timeout must be greater than 0");
		//---
		_timeout = timeout;
		return this;
	}

	public ShinyNotification build() {
		return new ShinyNotification(_message, _type, _timeout);
	}
}
