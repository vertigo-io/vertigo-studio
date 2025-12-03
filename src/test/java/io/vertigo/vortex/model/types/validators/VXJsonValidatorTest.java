package io.vertigo.vortex.model.types.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VXJsonValidatorTest {

    private final VXJsonValidator jsonValidator = new VXJsonValidator();

    @Test
    void shouldValidateCorrectJson() {
        // Given
        final String validJson = "{\"key\": \"value\"}";
        // When
        Assertions.assertDoesNotThrow(() -> jsonValidator.check(validJson));
        // Then - no exception is thrown
    }

    @Test
    void shouldValidateCorrectJsonArray() {
        // Given
        final String validJson = "[{\"key\": \"value\"}, {\"key2\": \"value2\"}]";
        // When
        Assertions.assertDoesNotThrow(() -> jsonValidator.check(validJson));
        // Then - no exception is thrown
    }

    @Test
    void shouldNotValidateIncorrectJson() {
        // Given
        final String invalidJson = "{\"key\": \"value\""; // Missing closing brace
        // When
        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> jsonValidator.check(invalidJson));
        // Then
        Assertions.assertEquals(jsonValidator.getErrorMessage().getDisplay(), exception.getMessage());
    }

    @Test
    void shouldNotValidateNonJsonString() {
        // Given
        final String nonJsonString = "this is not json";
        // When
        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> jsonValidator.check(nonJsonString));
        // Then
        Assertions.assertEquals(jsonValidator.getErrorMessage().getDisplay(), exception.getMessage());
    }

    @Test
    void shouldValidateNullString() {
        // Given
        final String nullString = null;
        // When
        Assertions.assertDoesNotThrow(() -> jsonValidator.check(nullString));
        // Then - no exception is thrown
    }

    @Test
    void shouldValidateEmptyString() {
        // Given
        final String emptyString = "";
        // When
        Assertions.assertDoesNotThrow(() -> jsonValidator.check(emptyString));
        // Then - no exception is thrown
    }

    @Test
    void shouldReturnCorrectErrorMessage() {
        // When
        final String errorMessage = jsonValidator.getErrorMessage().getDisplay();
        // Then
        Assertions.assertEquals("Value is not a valid JSON.", errorMessage);
    }

    @Test
    void shouldReturnEmptyProperty() {
        // When
        final var property = jsonValidator.getProperty();
        // Then
        Assertions.assertTrue(property.isEmpty());
    }
}
