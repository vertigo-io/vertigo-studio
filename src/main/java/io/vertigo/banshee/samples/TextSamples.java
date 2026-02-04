package io.vertigo.banshee.samples;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;

final class TextSamples {
	static ShinyModel figlet() {
		return Shiny.figlet()
				.withText("Hello Vertigo")
				.build();
	}

	static ShinyModel textpath() {
		return Shiny.textPath()
				.withPath("root/node/leaf")
				.withSeparator("/")
				.build();
	}

	static ShinyModel title() {
		return Shiny.title()
				.withText("This is a title")
				.withLevel(2)
				.build();
	}

	static ShinyModel paragraph() {
		return Shiny.paragraph()
				.withText("This is a paragraph.")
				.build();
	}

}
