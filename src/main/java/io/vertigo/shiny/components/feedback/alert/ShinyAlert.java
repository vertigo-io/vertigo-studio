package io.vertigo.shiny.components.feedback.alert;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyAlert(
		ShinyAlertType alertType,
		String title,
		String content,
		boolean closable,
		String icon,
		boolean prominent) implements ShinyComponent {

	@Override
	public String type() {
		return "alert";
	}

	public ShinyAlert {
		Assertion.check()
				.isNotNull(alertType, "Type cannot be null")
				.isNotBlank(content, "Content cannot be blank");
	}
}
