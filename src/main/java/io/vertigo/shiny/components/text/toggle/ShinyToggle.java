package io.vertigo.shiny.components.text.toggle;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyToggle(
		String label,
		boolean value,
		ShinyToggleType type,
		String onText,
		String offText,
		boolean showText) implements ShinyComponent {
}
