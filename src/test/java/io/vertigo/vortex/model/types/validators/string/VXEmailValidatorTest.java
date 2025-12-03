package io.vertigo.vortex.model.types.validators.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VXEmailValidatorTest {

	private final VXEmailValidator validator = new VXEmailValidator();

	@Test
	void shouldValidateCorrectEmail() {
		// Given
		final String validEmail = "test@example.com";
		// When
		Assertions.assertTrue(validator.isValid(validEmail));
		// Then - no exception is thrown
	}

	@Test
	void shouldValidateCorrectEmailWithSubdomain() {
		// Given
		final String validEmail = "test@sub.example.com";
		// When
		Assertions.assertTrue(validator.isValid(validEmail));
		// Then - no exception is thrown
	}

	@Test
	void shouldValidateCorrectEmailWithNumbers() {
		// Given
		final String validEmail = "test123@example.com";
		// When
		Assertions.assertTrue(validator.isValid(validEmail));
		// Then - no exception is thrown
	}

	@Test
	void shouldNotValidateEmailWithoutAtSign() {
		// Given
		final String invalidEmail = "testexample.com";
		// When
		Assertions.assertFalse(validator.isValid(invalidEmail));
	}

	@Test
	void shouldNotValidateEmailWithoutDomain() {
		// Given
		final String invalidEmail = "test@.com";
		// When
		Assertions.assertFalse(validator.isValid(invalidEmail));
	}

	@Test
	void shouldNotValidateEmailWithoutTopLevelDomain() {
		// Given
		final String invalidEmail = "test@example";
		// When
		Assertions.assertFalse(validator.isValid(invalidEmail));
	}

	@Test
	void shouldNotValidateEmailWithInvalidCharacters() {
		// Given
		final String invalidEmail = "test@exam_ple.com";
		// When
		Assertions.assertFalse(validator.isValid(invalidEmail));
	}

	@Test
	void shouldValidateNullEmail() {
		// Given
		final String nullEmail = null;
		// When
		Assertions.assertTrue(validator.isValid(nullEmail));
		// Then - no exception is thrown
	}

	@Test
	void shouldValidateEmptyEmail() {
		// Given
		final String emptyEmail = "";
		// When
		Assertions.assertFalse(validator.isValid(emptyEmail)); // Empty string is not a valid email
	}

	@Test
	void shouldReturnCorrectErrorMessage() {
		// When
		final String errorMessage = validator.getErrorMessage().getDisplay();
		// Then
		Assertions.assertEquals("The value must be a valid email address.", errorMessage);
	}

	@Test
	void shouldReturnEmptyProperty() {
		// When
		final var property = validator.getProperty();
		// Then
		Assertions.assertTrue(property.isEmpty());
	}
}
