package io.vertigo.shiny.components.dataviz.rating;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyEffects;

public record ShinyRating(
		String label,
		double value,
		ShinyRatingScale scale,
		int customMaxValue, // -1 means use scale
		ShinyRatingStyle style,
		boolean showValue,
		boolean showPercentage,
		boolean showBox,
		String separator,
		boolean allowHalfRating) implements ShinyComponent {

	// Package-private constructor, only accessible by the Builder
	public ShinyRating {
	}

	// Static factory method to get a new Builder instance
	public static ShinyRatingBuilder builder() {
		return new ShinyRatingBuilder();
	}

	public void render(final ShinyWriter writer) {
		final int maxValue = getEffectiveMaxValue();
		final double clampedValue = Math.min(value, maxValue);

		final StringBuilder rating = new StringBuilder();

		// Add label if present
		if (label != null && !label.isEmpty()) {
			writer.print(label).print(": ");
		}

		// Build the rating visualization
		if (scale == ShinyRatingScale.SCALE_100 && maxValue == 100) {
			// For scale 100, show as progress bar
			buildProgressBarRating(rating, clampedValue, maxValue);
		} else {
			// For other scales, show individual icons
			buildIconRating(rating, clampedValue, maxValue);
		}

		// Add value/percentage display
		if (showValue || showPercentage) {
			rating.append(" ");
			if (showValue) {
				rating.append(String.format("%.1f/%d", clampedValue, maxValue));
			}
			if (showValue && showPercentage) {
				rating.append(" ");
			}
			if (showPercentage) {
				final double percentage = (clampedValue / maxValue) * 100;
				rating.append(String.format("(%.1f%%)", percentage));
			}
		}

		final String result = rating.toString();

		if (showBox) {
			printWithBox(writer, result);
		} else {
			writer.println(result);
		}
	}

	private void buildIconRating(final StringBuilder rating, final double clampedValue, final int maxValue) {
		for (int i = 1; i <= maxValue; i++) {
			if (i > 1 && !separator.isEmpty()) {
				rating.append(separator);
			}

			if (allowHalfRating && clampedValue >= i - 0.5 && clampedValue < i) {
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

	private void buildProgressBarRating(final StringBuilder rating, final double clampedValue, final int maxValue) {
		final int barLength = 20; // Fixed length for progress bar
		final double percentage = clampedValue / maxValue;
		final int filledLength = (int) (barLength * percentage);

		rating.append("[")
				.append(style.filledColor().fg(style.type().getFilledIcon().repeat(filledLength)))
				.append(style.emptyColor().fg(style.type().getEmptyIcon().repeat(barLength - filledLength)))
				.append("]");
	}

	private int getEffectiveMaxValue() {
		return customMaxValue > 0 ? customMaxValue : scale.getMaxValue();
	}

	private void printWithBox(final ShinyWriter writer, final String content) {
		// Calculate length without color codes for the box
		final String cleanContent = content.replaceAll("\\u001B\\[[;\\d]*m", "");
		final int contentLength = cleanContent.length();
		final int boxWidth = contentLength + 2;

		writer.println("┌" + "─".repeat(boxWidth) + "┐") //Top Border
				.println("│ " + content + " │") // Content Line
				.println("└" + "─".repeat(boxWidth) + "┘");// Bottom Border
	}
}
