package io.vertigo.shiny.renderers.dataviz;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.dataviz.rating.ShinyRating;
import io.vertigo.shiny.models.dataviz.rating.ShinyRatingScale;
import io.vertigo.shiny.renderers.ShinyComponentRenderer;
import io.vertigo.shiny.style.ShinyEffects;

public final class ShinyRatingRenderer implements ShinyComponentRenderer<ShinyRating> {
	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinyRating;
	}

	@Override
	public void render(final ShinyRating shinyRating) {
		Assertion.check()
				.isNotNull(shinyRating);
		//---
		final ShinyRatingStyle style = Shiny.theme().ratingStyle();
		final ShinyWriter writer = Shiny.writer();

		final int maxValue = getEffectiveMaxValue(shinyRating);
		final double clampedValue = Math.min(shinyRating.value(), maxValue);

		final StringBuilder rating = new StringBuilder();

		// Add label if present
		if (shinyRating.label() != null && !shinyRating.label().isEmpty()) {
			writer.print(shinyRating.label()).print(": ");
		}

		// Build the rating visualization
		if (shinyRating.scale() == ShinyRatingScale.SCALE_100 && maxValue == 100) {
			// For scale 100, show as progress bar
			buildProgressBarRating(shinyRating, style, rating, clampedValue, maxValue);
		} else {
			// For other scales, show individual icons
			buildIconRating(shinyRating, style, rating, clampedValue, maxValue);
		}

		// Add value/percentage display
		if (shinyRating.showValue() || shinyRating.showPercentage()) {
			rating.append(" ");
			if (shinyRating.showValue()) {
				rating.append(String.format("%.1f/%d", clampedValue, maxValue));
			}
			if (shinyRating.showValue() && shinyRating.showPercentage()) {
				rating.append(" ");
			}
			if (shinyRating.showPercentage()) {
				final double percentage = (clampedValue / maxValue) * 100;
				rating.append(String.format("(%.1f%%)", percentage));
			}
		}

		final String result = rating.toString();

		if (shinyRating.showBox()) {
			printWithBox(writer, result);
		} else {
			writer.println(result);
		}
	}

	private static void buildIconRating(final ShinyRating shinyRating, final ShinyRatingStyle style, final StringBuilder rating, final double clampedValue, final int maxValue) {
		for (int i = 1; i <= maxValue; i++) {
			if (i > 1 && !shinyRating.separator().isEmpty()) {
				rating.append(shinyRating.separator());
			}

			if (shinyRating.allowHalfRating() && clampedValue >= i - 0.5 && clampedValue < i) {
				// Half rating (for now, use filled icon with different color)
				rating.append(ShinyEffects.DIM.apply(
						style.filledColor().fg(style.type().getFilledIcon())));
			} else if (clampedValue >= i) {
				// Filled
				rating.append(style.filledColor().fg(style.type().getFilledIcon()));
			} else {
				// Empty
				rating.append(style.emptyColor().fg(style.type().getEmptyIcon()));
			}
		}
	}

	private static void buildProgressBarRating(final ShinyRating shinyRating, final ShinyRatingStyle style, final StringBuilder rating, final double clampedValue, final int maxValue) {
		final int barLength = 20; // Fixed length for progress bar
		final double percentage = clampedValue / maxValue;
		final int filledLength = (int) (barLength * percentage);

		rating.append("[")
				.append(style.filledColor().fg(style.type().getFilledIcon().repeat(filledLength)))
				.append(style.emptyColor().fg(style.type().getEmptyIcon().repeat(barLength - filledLength)))
				.append("]");
	}

	private static int getEffectiveMaxValue(final ShinyRating shinyRating) {
		return shinyRating.customMaxValue() > 0 ? shinyRating.customMaxValue() : shinyRating.scale().getMaxValue();
	}

	private static void printWithBox(final ShinyWriter writer, final String content) {
		// Calculate length without color codes for the box
		final String cleanContent = content.replaceAll("\\u001B\\[[;\\d]*m", "");
		final int contentLength = cleanContent.length();
		final int boxWidth = contentLength + 2;

		writer.println("┌" + "─".repeat(boxWidth) + "┐") //Top Border
				.println("│ " + content + " │") // Content Line
				.println("└" + "─".repeat(boxWidth) + "┘");// Bottom Border
	}
}
