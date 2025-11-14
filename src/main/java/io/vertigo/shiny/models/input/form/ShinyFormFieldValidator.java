package io.vertigo.shiny.models.input.form;

import java.util.regex.Pattern;

public record ShinyFormFieldValidator(
		String pattern,
		Integer minLength,
		Integer maxLength,
		Object minValue,
		Object maxValue) {

	public boolean validate(Object value) {
		if (value == null) {
			return true; // Or handle as needed, for now, we assume null is valid if not required
		}

		if (value instanceof String strValue) {
			if (minLength != null && strValue.length() < minLength) {
				return false;
			}
			if (maxLength != null && strValue.length() > maxLength) {
				return false;
			}
			if (pattern != null && !Pattern.matches(pattern, strValue)) {
				return false;
			}
		}

		if (value instanceof Number numValue) {
			if (minValue instanceof Number min) {
				if (numValue.doubleValue() < min.doubleValue()) {
					return false;
				}
			}
			if (maxValue instanceof Number max) {
				if (numValue.doubleValue() > max.doubleValue()) {
					return false;
				}
			}
		}

		// Assuming date validation would be handled similarly if we knew the date type
		// For now, this covers String and Number types.

		return true;
	}
}
