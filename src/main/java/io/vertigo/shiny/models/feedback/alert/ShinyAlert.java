package io.vertigo.shiny.models.feedback.alert;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyAlert(
		UUID id,
		ShinyAlertType alertType,
		String title,
		String content,
		boolean closable,
		String icon,
		boolean prominent) implements ShinyModel {

	public ShinyAlert {
		Assertion.check().isNotNull(id);
		Assertion.check()
				.isNotNull(alertType, "Type cannot be null")
				.isNotBlank(content, "Content cannot be blank");
	}

	@Override
	public String shinyType() {
		return "ShinyAlert";
	}

}
