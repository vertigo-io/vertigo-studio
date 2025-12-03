package io.vertigo.vortex.model.types.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VXSlugValidatorTest {

    private final VXSlugValidator slugValidator = new VXSlugValidator();

    @Test
    void shouldValidateCorrectSlug() {
        // Given
        final String validSlug = "this-is-a-valid-slug";
        // When
        Assertions.assertDoesNotThrow(() -> slugValidator.check(validSlug));
        // Then - no exception is thrown
    }

    @Test
    void shouldNotValidateSlugWithUpperCase() {
        // Given
        final String invalidSlug = "This-is-an-invalid-slug";
        // When
        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> slugValidator.check(invalidSlug));
        // Then
        Assertions.assertEquals(slugValidator.getErrorMessage().getDisplay(), exception.getMessage());
    }

    @Test
    void shouldNotValidateSlugWithSpaces() {
        // Given
        final String invalidSlug = "this is an invalid slug";
        // When
        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> slugValidator.check(invalidSlug));
        // Then
        Assertions.assertEquals(slugValidator.getErrorMessage().getDisplay(), exception.getMessage());
    }

    @Test
    void shouldNotValidateSlugStartingWithHyphen() {
        // Given
        final String invalidSlug = "-this-is-an-invalid-slug";
        // When
        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> slugValidator.check(invalidSlug));
        // Then
        Assertions.assertEquals(slugValidator.getErrorMessage().getDisplay(), exception.getMessage());
    }

    @Test
    void shouldNotValidateSlugEndingWithHyphen() {
        // Given
        final String invalidSlug = "this-is-an-invalid-slug-";
        // When
        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> slugValidator.check(invalidSlug));
        // Then
        Assertions.assertEquals(slugValidator.getErrorMessage().getDisplay(), exception.getMessage());
    }

    @Test
    void shouldValidateNullString() {
        // Given
        final String nullString = null;
        // When
        Assertions.assertDoesNotThrow(() -> slugValidator.check(nullString));
        // Then - no exception is thrown
    }

    @Test
    void shouldValidateEmptyString() {
        // Given
        final String emptyString = "";
        // When
        Assertions.assertDoesNotThrow(() -> slugValidator.check(emptyString));
        // Then - no exception is thrown
    }

    @Test
    void shouldReturnCorrectErrorMessage() {
        // When
        final String errorMessage = slugValidator.getErrorMessage().getDisplay();
        // Then
        Assertions.assertEquals("Value is not a valid URL slug. It should only contain lowercase letters, numbers, and hyphens, and not start or end with a hyphen.", errorMessage);
    }

    @Test
    void shouldReturnEmptyProperty() {
        // When
        final var property = slugValidator.getProperty();
        // Then
        Assertions.assertTrue(property.isEmpty());
    }
}
