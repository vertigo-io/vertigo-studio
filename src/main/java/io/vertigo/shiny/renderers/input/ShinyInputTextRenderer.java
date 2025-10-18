package io.vertigo.shiny.renderers.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.input.text.ShinyInputText;
import io.vertigo.shiny.renderers.ShinyComponentRenderer;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyInputTextRenderer implements ShinyComponentRenderer<ShinyInputText> {
	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinyInputText;
	}

	@Override
	public void render(final ShinyInputText shinyInputText) {
		Assertion.check()
				.isNotNull(shinyInputText);
		//---
		//		final ShinyInputTextStyle style = Shiny.theme().inputTextStyle();
		final ShinyWriter writer = Shiny.writer();

		String prompt = shinyInputText.label();
		if (shinyInputText.required()) {
			prompt += ShinyColors.RED.fg(" (required)");
		}
		if (shinyInputText.suggestions() != null && !shinyInputText.suggestions().isEmpty()) {
			prompt += " " + ShinyColors.CYAN.fg(shinyInputText.suggestions().toString());
		}
		if (shinyInputText.defaultValue() != null) {
			prompt += " " + ShinyColors.YELLOW.fg("[" + shinyInputText.defaultValue() + "]");
		}
		prompt += ": ";

		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean validInput = false;
		while (!validInput) {
			writer.print(prompt)
					.flush();

			try {
				String inputLine = reader.readLine();

				if (inputLine == null) {
					shinyInputText.value().set(null);
					validInput = true;
				} else {
					shinyInputText.value().set(inputLine.trim());

					if (shinyInputText.value().get().isEmpty() && shinyInputText.defaultValue() != null) {
						shinyInputText.value().set(shinyInputText.defaultValue());
					}

					if (shinyInputText.required() && shinyInputText.value().get().isEmpty()) {
						writer.println(ShinyColors.RED.fg("Input is required."));
					} else if (shinyInputText.validationPattern() != null && !shinyInputText.value().get().isEmpty()) {
						Matcher matcher = shinyInputText.validationPattern().matcher(shinyInputText.value().get());
						if (!matcher.matches()) {
							writer.println(ShinyColors.RED.fg("Input does not match the required pattern."));
						} else {
							validInput = true;
						}
					} else {
						validInput = true;
					}
				}
			} catch (IOException e) {
				writer.println(ShinyColors.RED.fg("Error reading input: " + e.getMessage()));
				shinyInputText.value().set(null);
				validInput = true;
			}
		}
	}
}
