package io.vertigo.vortex.model.types.validators.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.vortex.model.library.validators.string.VXJsonValidator;

public class VXJsonValidatorTest {

	private final VXJsonValidator jsonValidator = new VXJsonValidator();

	@Test
	void shouldValidateCorrectJson() {
		// Given
		final String validJson = "{\"key\": \"value\"}";
		// When
		Assertions.assertTrue(jsonValidator.isValid(validJson));
		// Then - no exception is thrown
	}

	@Test
	void shouldValidateCorrectJsonText() {
		// Given
		final String validJson = """
				{"key": "value"}
				""";
		// When
		Assertions.assertTrue(jsonValidator.isValid(validJson));
		// Then - no exception is thrown
	}

	@Test
	void shouldValidateCorrectJsonArray() {
		// Given
		final String validJson = "[{\"key\": \"value\"}, {\"key2\": \"value2\"}]";
		// When
		Assertions.assertTrue(jsonValidator.isValid(validJson));
		// Then - no exception is thrown
	}

	@Test
	void shouldNotValidateIncorrectJson() {
		// Given
		final String invalidJson = "{\"key\": \"value\""; // Missing closing brace
		// When
		Assertions.assertFalse(jsonValidator.isValid(invalidJson));
	}

	@Test
	void shouldNotValidateNonJsonString() {
		// Given
		final String nonJsonString = "this is not json";
		// When
		Assertions.assertFalse(jsonValidator.isValid(nonJsonString));
	}

	@Test
	void shouldValidateNullString() {
		// Given
		final String nullString = null;
		// When
		Assertions.assertTrue(jsonValidator.isValid(nullString));
		// Then - no exception is thrown
	}

	@Test
	void shouldValidateEmptyString() {
		// Given
		final String emptyString = "";
		// When
		Assertions.assertTrue(jsonValidator.isValid(emptyString));
		// Then - no exception is thrown
	}

	@Test
	void shouldReturnCorrectErrorMessage() {
		// When
		final String errorMessage = jsonValidator.getErrorMessage().getDisplay();
		// Then
		Assertions.assertEquals("Value is not a valid JSON.", errorMessage);
	}

}
