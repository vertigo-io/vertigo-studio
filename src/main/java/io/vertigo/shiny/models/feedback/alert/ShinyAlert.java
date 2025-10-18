package io.vertigo.shiny.models.feedback.alert;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyAlert(
		ShinyAlertType alertType,
		String title,
		String content,
		boolean closable,
		String icon,
		boolean prominent) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyAlert";
	}

	public ShinyAlert {
		Assertion.check()
				.isNotNull(alertType, "Type cannot be null")
				.isNotBlank(content, "Content cannot be blank");
	}
}
