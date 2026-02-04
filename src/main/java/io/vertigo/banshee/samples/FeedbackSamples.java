package io.vertigo.banshee.samples;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.feedback.alert.ShinyAlertType;
import io.vertigo.shiny.models.feedback.notification.ShinyNotificationType;

final class FeedbackSamples {
	static ShinyModel alertInfo() {
		return Shiny.alert()
				.withAlertType(ShinyAlertType.INFO)
				.withTitle("Information")
				.withContent("This is an informational message.")
				.withClosable(true)
				.build();
	}

	static ShinyModel alertWarning() {
		return Shiny.alert()
				.withAlertType(ShinyAlertType.WARNING)
				.withContent("This is a warning message, please be careful.")
				.build();
	}

	static ShinyModel alertSuccess() {
		return Shiny.alert()
				.withAlertType(ShinyAlertType.SUCCESS)
				.withTitle("Success")
				.withContent("The operation completed successfully!")
				.build();
	}

	static ShinyModel alertError() {
		return Shiny.alert()
				.withAlertType(ShinyAlertType.ERROR)
				.withTitle("Error")
				.withContent("An error occurred while processing your request.")
				.build();
	}

	static ShinyModel modal() {
		return Shiny.modal()
				.withTitle("My Modal")
				.withContent(
						Shiny.container()
								.addModel(
										Shiny.paragraph()
												.withText("This is the content of the modal.")
												.build())
								.build())
				.isPersistent()
				.build();
	}

	static ShinyModel notification() {
		return Shiny.notification()
				.withType(ShinyNotificationType.SUCCESS)
				.withMessage("This is a success notification")
				.withTimeout(3000)
				.build();
	}
}
