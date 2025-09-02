package io.vertigo.shell.shiny.rating;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.ShinyComponent;
import io.vertigo.shell.shiny.utils.ShinyColor;
import io.vertigo.shell.shiny.utils.ShinyColors;

public final class ShinyRating implements ShinyComponent {

	private final Shiny shiny;
	private String label;
	private double value = 0;
	private ShinyRatingScale scale = ShinyRatingScale.SCALE_5;
	private int customMaxValue = -1; // -1 means use scale
	private ShinyRatingStyle style = ShinyRatingStyle.STAR;
	private ShinyColor filledColor = ShinyColors.YELLOW;
	private ShinyColor emptyColor = ShinyColors.WHITE;
	private boolean showValue = true;
	private boolean showPercentage = false;
	private boolean showBox = false;
	private String separator = "";
	private boolean allowHalfRating = false;

	public ShinyRating(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
	}

	public ShinyRating label(final String text) {
		this.label = text;
		return this;
	}

	public ShinyRating value(final double currentValue) {
		this.value = Math.max(0, currentValue); // Ensure non-negative
		return this;
	}

	public ShinyRating scale(final ShinyRatingScale ratingScale) {
		this.scale = ratingScale;
		this.customMaxValue = -1; // Reset custom max when using scale
		return this;
	}

	public ShinyRating maxValue(final int max) {
		this.customMaxValue = Math.max(1, max); // Ensure positive
		return this;
	}

	public ShinyRating style(final ShinyRatingStyle ratingStyle) {
		this.style = ratingStyle;
		return this;
	}

	public ShinyRating filledColor(final ShinyColor color) {
		this.filledColor = color;
		return this;
	}

	public ShinyRating emptyColor(final ShinyColor color) {
		this.emptyColor = color;
		return this;
	}

	public ShinyRating showValue(final boolean show) {
		this.showValue = show;
		return this;
	}

	public ShinyRating showPercentage(final boolean show) {
		this.showPercentage = show;
		return this;
	}

	public ShinyRating showBox(final boolean show) {
		this.showBox = show;
		return this;
	}

	public ShinyRating separator(final String sep) {
		this.separator = sep != null ? sep : "";
		return this;
	}

	public ShinyRating allowHalfRating(final boolean allow) {
		this.allowHalfRating = allow;
		return this;
	}

	public void print() {
		final int maxValue = getEffectiveMaxValue();
		final double clampedValue = Math.min(value, maxValue);

		final StringBuilder rating = new StringBuilder();

		// Add label if present
		if (label != null && !label.isEmpty()) {
			rating.append(label).append(": ");
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
			printWithBox(result);
		} else {
			shiny.getWriter().println(result);
		}
	}

	private void buildIconRating(final StringBuilder rating, final double clampedValue, final int maxValue) {
		for (int i = 1; i <= maxValue; i++) {
			if (i > 1 && !separator.isEmpty()) {
				rating.append(separator);
			}

			if (allowHalfRating && clampedValue >= i - 0.5 && clampedValue < i) {
				// Half rating (for now, use filled icon with different color)
				rating.append(ShinyColors.DIM)
						.append(filledColor.fg())
						.append(style.getFilledIcon())
						.append(ShinyColors.RESET);
			} else if (clampedValue >= i) {
				// Filled
				rating.append(filledColor.fg())
						.append(style.getFilledIcon())
						.append(ShinyColors.RESET);
			} else {
				// Empty
				rating.append(emptyColor.fg())
						.append(style.getEmptyIcon())
						.append(ShinyColors.RESET);
			}
		}
	}

	private void buildProgressBarRating(final StringBuilder rating, final double clampedValue, final int maxValue) {
		final int barLength = 20; // Fixed length for progress bar
		final double percentage = clampedValue / maxValue;
		final int filledLength = (int) (barLength * percentage);

		rating.append("[")
				.append(filledColor)
				.append(style.getFilledIcon().repeat(filledLength))
				.append(emptyColor)
				.append(style.getEmptyIcon().repeat(barLength - filledLength))
				.append(ShinyColors.RESET)
				.append("]");
	}

	private int getEffectiveMaxValue() {
		return customMaxValue > 0 ? customMaxValue : scale.getMaxValue();
	}

	private void printWithBox(final String content) {
		// Calculate length without color codes for the box
		final String cleanContent = content.replaceAll("\\u001B\\[[;\\d]*m", "");
		final int contentLength = cleanContent.length();
		final int boxWidth = contentLength + 2;

		final String topBorder = "┌" + "─".repeat(boxWidth) + "┐";
		final String bottomBorder = "└" + "─".repeat(boxWidth) + "┘";
		final String contentLine = "│ " + content + " │";

		shiny.getWriter().println(topBorder);
		shiny.getWriter().println(contentLine);
		shiny.getWriter().println(bottomBorder);
	}
	//
	//	// Utility method to display multiple ratings aligned
	//	public static void printMultiple(final Shiny shiny, final java.util.Map<String, Double> ratings,
	//			final RatingStyle style, final RatingScale scale) {
	//		Assertion.check().isNotNull(shiny);
	//		Assertion.check().isNotNull(ratings);
	//		//---
	//
	//		if (ratings.isEmpty()) {
	//			shiny.getWriter().println("No ratings to display");
	//			return;
	//		}
	//
	//		// Find maximum label length
	//		final int maxLabelLength = ratings.keySet().stream()
	//				.mapToInt(String::length)
	//				.max()
	//				.orElse(0);
	//
	//		// Display each rating aligned
	//		ratings.forEach((label, value) -> {
	//			final String paddedLabel = String.format("%-" + maxLabelLength + "s", label);
	//			new ShinyRating(shiny)
	//					.label(paddedLabel)
	//					.value(value)
	//					.style(style)
	//					.scale(scale)
	//					.print();
	//		});
	//	}
	//
	//	// Utility method to create a rating dashboard
	//	public static void printDashboard(final Shiny shiny, final String title,
	//			final java.util.Map<String, Double> ratings,
	//			final RatingStyle style, final RatingScale scale) {
	//		Assertion.check().isNotNull(shiny);
	//		Assertion.check().isNotNull(ratings);
	//		//---
	//
	//		if (title != null && !title.isEmpty()) {
	//			shiny.getWriter().println(ShinyColors.BOLD + title + ShinyColors.RESET);
	//			shiny.getWriter().println("─".repeat(title.length()));
	//		}
	//
	//		printMultiple(shiny, ratings, style, scale);
	//		shiny.getWriter().println();
	//	}
	//
	//	// Utility method to create a summary rating
	//	public static void printSummary(final Shiny shiny, final String title,
	//			final java.util.Map<String, Double> ratings,
	//			final RatingStyle style, final RatingScale scale) {
	//		Assertion.check().isNotNull(shiny);
	//		Assertion.check().isNotNull(ratings);
	//		//---
	//
	//		if (ratings.isEmpty()) {
	//			shiny.getWriter().println("No ratings to summarize");
	//			return;
	//		}
	//
	//		// Calculate average
	//		final double average = ratings.values().stream()
	//				.mapToDouble(Double::doubleValue)
	//				.average()
	//				.orElse(0.0);
	//
	//		// Print individual ratings
	//		printDashboard(shiny, title, ratings, style, scale);
	//
	//		// Print average
	//		shiny.getWriter().println(ShinyColors.BOLD + "Average Rating:" + ShinyColors.RESET);
	//		new ShinyRating(shiny)
	//				.value(average)
	//				.style(style)
	//				.scale(scale)
	//				.showValue(true)
	//				.showPercentage(true)
	//				.showBox(true)
	//				.print();
	//		shiny.getWriter().println();
	//	}
}
