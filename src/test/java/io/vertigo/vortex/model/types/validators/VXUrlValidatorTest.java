package io.vertigo.vortex.model.types.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VXUrlValidatorTest {

    private final VXUrlValidator urlValidator = new VXUrlValidator();

    @Test
    void shouldValidateCorrectUrl() {
        // Given
        final String validUrl = "https://www.google.com";
        // When
        Assertions.assertDoesNotThrow(() -> urlValidator.check(validUrl));
        // Then - no exception is thrown
    }

    @Test
    void shouldValidateCorrectUrlWithPort() {
        // Given
        final String validUrl = "http://localhost:8080";
        // When
        Assertions.assertDoesNotThrow(() -> urlValidator.check(validUrl));
        // Then - no exception is thrown
    }

    @Test
    void shouldNotValidateIncorrectUrl() {
        // Given
        final String invalidUrl = "not a url";
        // When
        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> urlValidator.check(invalidUrl));
        // Then
        Assertions.assertEquals(urlValidator.getErrorMessage().getDisplay(), exception.getMessage());
    }

    @Test
    void shouldNotValidateUrlWithoutProtocol() {
        // Given
        final String invalidUrl = "www.google.com";
        // When
        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> urlValidator.check(invalidUrl));
        // Then
        Assertions.assertEquals(urlValidator.getErrorMessage().getDisplay(), exception.getMessage());
    }

    @Test
    void shouldValidateNullString() {
        // Given
        final String nullString = null;
        // When
        Assertions.assertDoesNotThrow(() -> urlValidator.check(nullString));
        // Then - no exception is thrown
    }

    @Test
    void shouldValidateEmptyString() {
        // Given
        final String emptyString = "";
        // When
        Assertions.assertDoesNotThrow(() -> urlValidator.check(emptyString));
        // Then - no exception is thrown
    }

    @Test
    void shouldReturnCorrectErrorMessage() {
        // When
        final String errorMessage = urlValidator.getErrorMessage().getDisplay();
        // Then
        Assertions.assertEquals("Value is not a valid URL.", errorMessage);
    }

    @Test
    void shouldReturnEmptyProperty() {
        // When
        final var property = urlValidator.getProperty();
        // Then
        Assertions.assertTrue(property.isEmpty());
    }
}
