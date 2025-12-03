package io.vertigo.vortex.model.types.validators.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VXStartsWithValidatorTest {

	@Test
	void shouldValidateStringStartingWithPrefix() {
		// Given
		final VXStartsWithValidator validator = new VXStartsWithValidator("test");
		final String value = "test-string";
		// When
		Assertions.assertTrue(validator.isValid(value));
		// Then - no exception is thrown
	}

	@Test
	void shouldNotValidateStringNotStartingWithPrefix() {
		// Given
		final VXStartsWithValidator validator = new VXStartsWithValidator("test");
		final String value = "string-test";
		// When
		Assertions.assertFalse(validator.isValid(value));
	}

	@Test
	void shouldValidateNullString() {
		// Given
		final VXStartsWithValidator validator = new VXStartsWithValidator("test");
		final String value = null;
		// When
		Assertions.assertTrue(validator.isValid(value));
		// Then - no exception is thrown
	}

	@Test
	void shouldReturnCorrectErrorMessage() {
		// Given
		final VXStartsWithValidator validator = new VXStartsWithValidator("test");
		// When
		final String errorMessage = validator.getErrorMessage().getDisplay();
		// Then
		Assertions.assertEquals("The value must start with 'test'.", errorMessage);
	}

}
