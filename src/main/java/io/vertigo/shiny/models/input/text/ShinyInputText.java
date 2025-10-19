package io.vertigo.shiny.models.input.text;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import io.vertigo.shiny.ShinyMagicBox;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyInputText(
		UUID id,
		String label,
		boolean required,
		Pattern validationPattern,
		List<String> suggestions,
		String defaultValue,
		ShinyMagicBox<String> value) implements ShinyModel {
}
