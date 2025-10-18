package io.vertigo.shiny.models.text.toggle;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyToggle(
		String label,
		boolean value,
		ShinyToggleType toggleType,
		String onText,
		String offText,
		boolean showText) implements ShinyModel {

	public String shinyType() {
		return "ShinyToggle";
	}
}
