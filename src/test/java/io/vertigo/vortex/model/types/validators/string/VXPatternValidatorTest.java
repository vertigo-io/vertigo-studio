package io.vertigo.vortex.model.types.validators.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VXPatternValidatorTest {

	@Test
	void shouldValidateStringMatchingPattern() {
		// Given
		final VXPatternValidator validator = new VXPatternValidator("^[a-z]+$");
		final String matchingString = "helloworld";
		// When
		Assertions.assertTrue(validator.isValid(matchingString));
		// Then - no exception is thrown
	}

	@Test
	void shouldNotValidateStringNotMatchingPattern() {
		// Given
		final VXPatternValidator validator = new VXPatternValidator("^[a-z]+$");
		final String notMatchingString = "helloWorld1";
		// When
		Assertions.assertFalse(validator.isValid(notMatchingString));
	}

	@Test
	void shouldValidateNullString() {
		// Given
		final VXPatternValidator validator = new VXPatternValidator("^[a-z]+$");
		final String nullString = null;
		// When
		Assertions.assertTrue(validator.isValid(nullString));
		// Then - no exception is thrown
	}

	@Test
	void shouldReturnCorrectErrorMessage() {
		// Given
		final VXPatternValidator validator = new VXPatternValidator("^[a-z]+$");
		// When
		final String errorMessage = validator.getErrorMessage().getDisplay();
		// Then
		Assertions.assertEquals("The value does not match the expected pattern", errorMessage);
	}

	@Test
	void shouldReturnCorrectProperty() {
		// Given
		String pattern = "^[a-z]+$";
		final VXPatternValidator validator = new VXPatternValidator(pattern);
		// When
		final var property = validator.getProperty();
		// Then
		Assertions.assertEquals("pattern", property.key().name());
		Assertions.assertEquals("^[a-z]+$", property.value());
	}
}
