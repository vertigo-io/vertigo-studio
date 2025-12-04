package io.vertigo.vortex.model.types.validators.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.vortex.gold.library.validators.string.VXMinLengthValidator;

public class VXMinLengthValidatorTest {

	@Test
	void shouldValidateStringLongerThanMinLength() {
		// Given
		final VXMinLengthValidator validator = new VXMinLengthValidator(5);
		final String value = "abcdef";
		// When
		Assertions.assertTrue(validator.isValid(value));
		// Then - no exception is thrown
	}

	@Test
	void shouldValidateStringEqualToMinLength() {
		// Given
		final VXMinLengthValidator validator = new VXMinLengthValidator(5);
		final String value = "abcde";
		// When
		Assertions.assertTrue(validator.isValid(value));
		// Then - no exception is thrown
	}

	@Test
	void shouldNotValidateStringShorterThanMinLength() {
		// Given
		final VXMinLengthValidator validator = new VXMinLengthValidator(5);
		final String value = "abcd";
		// When
		Assertions.assertFalse(validator.isValid(value));
	}

	@Test
	void shouldValidateNullString() {
		// Given
		final VXMinLengthValidator validator = new VXMinLengthValidator(5);
		final String value = null;
		// When
		Assertions.assertTrue(validator.isValid(value));
		// Then - no exception is thrown
	}

	@Test
	void shouldReturnCorrectErrorMessage() {
		// Given
		final VXMinLengthValidator validator = new VXMinLengthValidator(5);
		// When
		final String errorMessage = validator.getErrorMessage().getDisplay();
		// Then
		Assertions.assertEquals("The length must be greater than or equal to 5", errorMessage);
	}

	@Test
	void shouldReturnCorrectProperty() {
		// Given
		final VXMinLengthValidator validator = new VXMinLengthValidator(5);
		// When
		final var property = validator.getProperty();
		// Then
		Assertions.assertEquals("minLength", property.key().name());
		Assertions.assertEquals(5, property.value());
	}
}
