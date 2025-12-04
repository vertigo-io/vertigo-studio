package io.vertigo.vortex.model.types.validators.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.vortex.gold.library.validators.string.VXContainsValidator;

public class VXContainsValidatorTest {

	@Test
	void shouldValidateStringContainingSubstring() {
		// Given
		final VXContainsValidator validator = new VXContainsValidator("test");
		final String value = "this-is-a-test-string";
		// When
		Assertions.assertTrue(validator.isValid(value));
		// Then - no exception is thrown
	}

	@Test
	void shouldNotValidateStringNotContainingSubstring() {
		// Given
		final VXContainsValidator validator = new VXContainsValidator("test");
		final String value = "this-is-a-string";
		// When
		Assertions.assertFalse(validator.isValid(value));
	}

	@Test
	void shouldValidateNullString() {
		// Given
		final VXContainsValidator validator = new VXContainsValidator("test");
		final String value = null;
		// When
		Assertions.assertTrue(validator.isValid(value));
		// Then - no exception is thrown
	}

	@Test
	void shouldReturnCorrectErrorMessage() {
		// Given
		final VXContainsValidator validator = new VXContainsValidator("test");
		// When
		final String errorMessage = validator.getErrorMessage().getDisplay();
		// Then
		Assertions.assertEquals("The value must contain 'test'.", errorMessage);
	}

	@Test
	void shouldReturnCorrectProperty() {
		// Given
		final VXContainsValidator validator = new VXContainsValidator("test");
		// When
		final var property = validator.getProperty();
		// Then
		Assertions.assertEquals("contains", property.key().name());
		Assertions.assertEquals("test", property.value());
	}
}
