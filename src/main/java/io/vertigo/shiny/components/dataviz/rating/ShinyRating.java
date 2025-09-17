package io.vertigo.shiny.components.dataviz.rating;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyEffects;

public final class ShinyRating implements ShinyComponent {

	private final String label;
	private final double value;
	private final ShinyRatingScale scale;
	private final int customMaxValue; // -1 means use scale
	private final ShinyRatingStyle ratingStyle;
	private final boolean showValue;
	private final boolean showPercentage;
	private final boolean showBox;
	private final String separator;
	private final boolean allowHalfRating;

	// Package-private constructor, only accessible by the Builder
	ShinyRating(ShinyRatingBuilder builder) {
		Assertion.check()
				.isNotNull(builder);
		//---
		this.label = builder.label;
		this.value = builder.value;
		this.scale = builder.scale;
		this.customMaxValue = builder.customMaxValue;
		this.ratingStyle = builder.ratingStyle;
		this.showValue = builder.showValue;
		this.showPercentage = builder.showPercentage;
		this.showBox = builder.showBox;
		this.separator = builder.separator;
		this.allowHalfRating = builder.allowHalfRating;
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
						ratingStyle.filledColor().fg(ratingStyle.type().getFilledIcon())));
			} else if (clampedValue >= i) {
				// Filled
				rating.append(ratingStyle.filledColor().fg(ratingStyle.type().getFilledIcon()));
			} else {
				// Empty
				rating.append(ratingStyle.emptyColor().fg(ratingStyle.type().getEmptyIcon()));
			}
		}
	}

	private void buildProgressBarRating(final StringBuilder rating, final double clampedValue, final int maxValue) {
		final int barLength = 20; // Fixed length for progress bar
		final double percentage = clampedValue / maxValue;
		final int filledLength = (int) (barLength * percentage);

		rating.append("[")
				.append(ratingStyle.filledColor().fg(ratingStyle.type().getFilledIcon().repeat(filledLength)))
				.append(ratingStyle.emptyColor().fg(ratingStyle.type().getEmptyIcon().repeat(barLength - filledLength)))
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
