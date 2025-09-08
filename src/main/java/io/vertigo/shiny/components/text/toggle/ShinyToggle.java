package io.vertigo.shiny.components.text.toggle;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyToggle implements ShinyComponent {
	private String label;
	private boolean value;
	private ShinyToggleStyle style = ShinyToggleStyle.TOGGLE;
	private ShinyColor onColor = ShinyColors.GREEN;
	private ShinyColor offColor = ShinyColors.RED;
	private String onText = "ON";
	private String offText = "OFF";
	private boolean showText = true;
	//	private boolean showBox = false;

	public ShinyToggle() {
	}

	public ShinyToggle label(final String text) {
		this.label = text;
		return this;
	}

	public ShinyToggle value(final boolean currentValue) {
		this.value = currentValue;
		return this;
	}

	public ShinyToggle style(final ShinyToggleStyle toggleStyle) {
		this.style = toggleStyle;
		return this;
	}

	public ShinyToggle onColor(final ShinyColor color) {
		this.onColor = color;
		return this;
	}

	public ShinyToggle offColor(final ShinyColor color) {
		this.offColor = color;
		return this;
	}

	public ShinyToggle onText(final String text) {
		this.onText = text;
		return this;
	}

	public ShinyToggle offText(final String text) {
		this.offText = text;
		return this;
	}

	public ShinyToggle showText(final boolean show) {
		this.showText = show;
		return this;
	}

	//	public ShinyToggle showBox(final boolean show) {
	//		this.showBox = show;
	//		return this;
	//	}

	public void render(final ShinyWriter writer) {
		final String icon = value ? style.getOnIcon() : style.getOffIcon();
		final ShinyColor color = value ? onColor : offColor;
		final String text = showText ? (value ? onText : offText) : "";

		final StringBuilder toggle = new StringBuilder();

		// Ajouter le label si présent
		if (label != null && !label.isEmpty()) {
			toggle.append(label).append(": ");
		}

		// Construire la représentation du toggle
		toggle.append(color.fg(icon
				+ ((showText && !text.isEmpty())
						? " " + text
						: "")));

		final String result = toggle.toString();
		//
		//		if (showBox) {
		//			printWithBox(result);
		//		} else {
		writer.println(result);
		//		}
	}

	//	private void printWithBox(final String content) {
	//		// Calculer la longueur sans les codes de couleur pour le box
	//		final String cleanContent = content.replaceAll("\\u001B\\[[;\\d]*m", "");
	//		final int boxWidth = cleanContent.length() + 2;
	//
	//		final String UNICODE_VERTICAL = "│"; // U+2502
	//		final String UNICODE_SQUARE_TOP_LEFT = "┌"; // U+250C
	//		final String UNICODE_SQUARE_TOP_RIGHT = "┐"; // U+2510
	//
	//		final String UNICODE_SQUARE_BOTTOM_LEFT = "└"; // U+2514
	//		final String UNICODE_SQUARE_BOTTOM_RIGHT = "┘"; // U+2518
	//
	//		final String topBorder = UNICODE_SQUARE_TOP_LEFT + "─".repeat(boxWidth) + UNICODE_SQUARE_TOP_RIGHT;
	//		final String contentLine = UNICODE_VERTICAL + "o" + "x".repeat(boxWidth - 2) /*content.replace(" ", "x")*/ + "o" + UNICODE_VERTICAL;
	//		final String bottomBorder = UNICODE_SQUARE_BOTTOM_LEFT + "─".repeat(boxWidth) + UNICODE_SQUARE_BOTTOM_RIGHT;
	//
	//		shiny.getWriter().println(topBorder);
	//		shiny.getWriter().println(contentLine);
	//		shiny.getWriter().println(bottomBorder);
	//	}
	//
	//	// Méthode utilitaire pour afficher plusieurs toggles alignés
	//	public static void printMultiple(final java.util.Map<String, Boolean> toggles, final ToggleStyle style) {
	//		Assertion.check().isNotNull(toggles);
	//		//---
	//
	//		if (toggles.isEmpty()) {
	//			Shiny.getWriter().println("No toggles to display");
	//			return;
	//		}
	//
	//		// Trouver la longueur maximale des labels
	//		final int maxLabelLength = toggles.keySet().stream()
	//				.mapToInt(String::length)
	//				.max()
	//				.orElse(0);
	//
	//		// Afficher chaque toggle aligné
	//		toggles.forEach((label, value) -> {
	//			final String paddedLabel = String.format("%-" + maxLabelLength + "s", label);
	//			new ShinyToggle(shiny)
	//					.label(paddedLabel)
	//					.value(value)
	//					.style(style)
	//					.print();
	//		});
	//	}
}
