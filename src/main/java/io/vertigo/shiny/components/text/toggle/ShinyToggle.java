package io.vertigo.shiny.components.text.toggle;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyToggle(
		String label,
		boolean value,
		ShinyToggleType toggleType,
		String onText,
		String offText,
		boolean showText) implements ShinyComponent {

	public String type() {
		return "ShinyToggle";
	}
}
