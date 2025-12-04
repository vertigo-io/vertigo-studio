package io.vertigo.vortex.model.types.validators.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.vortex.gold.library.validators.string.VXExactLengthValidator;

public class VXExactLengthValidatorTest {

	@Test
	void shouldValidateStringWithExactLength() {
		// Given
		final VXExactLengthValidator validator = new VXExactLengthValidator(5);
		final String value = "abcde";
		// When
		Assertions.assertTrue(validator.isValid(value));
		// Then - no exception is thrown
	}

	@Test
	void shouldNotValidateStringWithDifferentLength() {
		// Given
		final VXExactLengthValidator validator = new VXExactLengthValidator(5);
		final String value = "abcdef";
		// When
		Assertions.assertFalse(validator.isValid(value));
	}

	@Test
	void shouldValidateNullString() {
		// Given
		final VXExactLengthValidator validator = new VXExactLengthValidator(5);
		final String value = null;
		// When
		Assertions.assertTrue(validator.isValid(value));
		// Then - no exception is thrown
	}

	@Test
	void shouldReturnCorrectErrorMessage() {
		// Given
		final VXExactLengthValidator validator = new VXExactLengthValidator(5);
		// When
		final String errorMessage = validator.getErrorMessage().getDisplay();
		// Then
		Assertions.assertEquals("The length must be exactly 5 characters.", errorMessage);
	}
}
