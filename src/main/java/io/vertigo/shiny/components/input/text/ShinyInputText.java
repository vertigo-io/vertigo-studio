package io.vertigo.shiny.components.input.text;

import java.util.List;
import java.util.regex.Pattern;

import io.vertigo.shiny.ShinyMagicBox;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyInputText(
		String label,
		boolean required,
		Pattern validationPattern,
		List<String> suggestions,
		String defaultValue,
		ShinyMagicBox<String> value) implements ShinyComponent {

	// Compact constructor to initialize the mutable field
	public ShinyInputText {
	}

	// Static factory method to get a new Builder instance
	public static ShinyInputTextBuilder builder() {
		return new ShinyInputTextBuilder();
	}

	@Override
	public void render(final ShinyWriter writer) {
		new ShinyInputTextRenderer().render(this, writer);
	}
}
