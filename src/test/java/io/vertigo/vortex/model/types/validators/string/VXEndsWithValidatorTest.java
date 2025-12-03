package io.vertigo.vortex.model.types.validators.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VXEndsWithValidatorTest {

    @Test
    void shouldValidateStringEndingWithSuffix() {
        // Given
        final VXEndsWithValidator validator = new VXEndsWithValidator("test");
        final String value = "string-test";
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldNotValidateStringNotEndingWithSuffix() {
        // Given
        final VXEndsWithValidator validator = new VXEndsWithValidator("test");
        final String value = "test-string";
        // When
        Assertions.assertFalse(validator.isValid(value));
    }

    @Test
    void shouldValidateNullString() {
        // Given
        final VXEndsWithValidator validator = new VXEndsWithValidator("test");
        final String value = null;
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldReturnCorrectErrorMessage() {
        // Given
        final VXEndsWithValidator validator = new VXEndsWithValidator("test");
        // When
        final String errorMessage = validator.getErrorMessage().getDisplay();
        // Then
        Assertions.assertEquals("The value must end with 'test'.", errorMessage);
    }

    @Test
    void shouldReturnEmptyProperty() {
        // Given
        final VXEndsWithValidator validator = new VXEndsWithValidator("test");
        // When
        final var property = validator.getProperty();
        // Then
        Assertions.assertTrue(property.isEmpty());
    }
}
