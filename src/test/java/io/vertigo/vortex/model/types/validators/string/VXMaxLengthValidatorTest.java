package io.vertigo.vortex.model.types.validators.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.vortex.model.library.validators.string.VXMaxLengthValidator;

public class VXMaxLengthValidatorTest {

	@Test
	void shouldValidateStringShorterThanMaxLength() {
		// Given
		final VXMaxLengthValidator validator = new VXMaxLengthValidator(10);
		final String value = "short";
		// When
		Assertions.assertTrue(validator.isValid(value));
		// Then - no exception is thrown
	}

	@Test
	void shouldValidateStringEqualToMaxLength() {
		// Given
		final VXMaxLengthValidator validator = new VXMaxLengthValidator(10);
		final String value = "1234567890";
		// When
		Assertions.assertTrue(validator.isValid(value));
		// Then - no exception is thrown
	}

	@Test
	void shouldNotValidateStringLongerThanMaxLength() {
		// Given
		final VXMaxLengthValidator validator = new VXMaxLengthValidator(10);
		final String value = "thisisalongstring";
		// When
		Assertions.assertFalse(validator.isValid(value));
	}

	@Test
	void shouldValidateNullString() {
		// Given
		final VXMaxLengthValidator validator = new VXMaxLengthValidator(10);
		final String value = null;
		// When
		Assertions.assertTrue(validator.isValid(value));
		// Then - no exception is thrown
	}

	@Test
	void shouldReturnCorrectErrorMessage() {
		// Given
		final VXMaxLengthValidator validator = new VXMaxLengthValidator(10);
		// When
		final String errorMessage = validator.getErrorMessage().getDisplay();
		// Then
		Assertions.assertEquals("The value must be less than or equal to 10 characters.", errorMessage);
	}

	@Test
	void shouldReturnCorrectProperty() {
		// Given
		final VXMaxLengthValidator validator = new VXMaxLengthValidator(10);
		// When
		final var property = validator.getProperty();
		// Then
		Assertions.assertEquals("maxLength", property.key().name());
		Assertions.assertEquals(10, property.value());
	}
}
