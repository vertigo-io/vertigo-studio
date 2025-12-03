package io.vertigo.vortex.model.types.validators.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VXSlugValidatorTest {

	private final VXSlugValidator slugValidator = new VXSlugValidator();

	@Test
	void shouldValidateCorrectSlug() {
		// Given
		final String validSlug = "this-is-a-valid-slug";
		// When
		Assertions.assertTrue(slugValidator.isValid(validSlug));
		// Then - no exception is thrown
	}

	@Test
	void shouldNotValidateSlugWithUpperCase() {
		// Given
		final String invalidSlug = "This-is-an-invalid-slug";
		// When
		Assertions.assertFalse(slugValidator.isValid(invalidSlug));
	}

	@Test
	void shouldNotValidateSlugWithSpaces() {
		// Given
		final String invalidSlug = "this is an invalid slug";
		// When
		Assertions.assertFalse(slugValidator.isValid(invalidSlug));
	}

	@Test
	void shouldNotValidateSlugStartingWithHyphen() {
		// Given
		final String invalidSlug = "-this-is-an-invalid-slug";
		// When
		Assertions.assertFalse(slugValidator.isValid(invalidSlug));
	}

	@Test
	void shouldNotValidateSlugEndingWithHyphen() {
		// Given
		final String invalidSlug = "this-is-an-invalid-slug-";
		// When
		Assertions.assertFalse(slugValidator.isValid(invalidSlug));
	}

	@Test
	void shouldValidateNullString() {
		// Given
		final String nullString = null;
		// When
		Assertions.assertTrue(slugValidator.isValid(nullString));
		// Then - no exception is thrown
	}

	@Test
	void shouldValidateEmptyString() {
		// Given
		final String emptyString = "";
		// When
		Assertions.assertTrue(slugValidator.isValid(emptyString));
		// Then - no exception is thrown
	}

	@Test
	void shouldReturnCorrectErrorMessage() {
		// When
		final String errorMessage = slugValidator.getErrorMessage().getDisplay();
		// Then
		Assertions.assertEquals("Value is not a valid URL slug. It should only contain lowercase letters, numbers, and hyphens, and not start or end with a hyphen.", errorMessage);
	}

	@Test
	void shouldReturnEmptyProperty() {
		// When
		final var property = slugValidator.getProperty();
		// Then
		Assertions.assertTrue(property.isEmpty());
	}
}
