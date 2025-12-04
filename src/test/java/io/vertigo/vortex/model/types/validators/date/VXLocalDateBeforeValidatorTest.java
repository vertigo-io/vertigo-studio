package io.vertigo.vortex.model.types.validators.date;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.vortex.gold.library.validators.date.VXLocalDateBeforeValidator;

public class VXLocalDateBeforeValidatorTest {

    @Test
    void shouldValidateDateBefore() {
        // Given
        final LocalDate referenceDate = LocalDate.of(2023, 1, 1);
        final VXLocalDateBeforeValidator validator = new VXLocalDateBeforeValidator(referenceDate);
        final LocalDate value = LocalDate.of(2022, 12, 31);
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldNotValidateDateAfter() {
        // Given
        final LocalDate referenceDate = LocalDate.of(2023, 1, 1);
        final VXLocalDateBeforeValidator validator = new VXLocalDateBeforeValidator(referenceDate);
        final LocalDate value = LocalDate.of(2023, 1, 2);
        // When
        Assertions.assertFalse(validator.isValid(value));
    }

    @Test
    void shouldNotValidateDateEqual() {
        // Given
        final LocalDate referenceDate = LocalDate.of(2023, 1, 1);
        final VXLocalDateBeforeValidator validator = new VXLocalDateBeforeValidator(referenceDate);
        final LocalDate value = LocalDate.of(2023, 1, 1);
        // When
        Assertions.assertFalse(validator.isValid(value));
    }

    @Test
    void shouldValidateNullDate() {
        // Given
        final LocalDate referenceDate = LocalDate.of(2023, 1, 1);
        final VXLocalDateBeforeValidator validator = new VXLocalDateBeforeValidator(referenceDate);
        final LocalDate value = null;
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldReturnCorrectErrorMessage() {
        // Given
        final LocalDate referenceDate = LocalDate.of(2023, 1, 1);
        final VXLocalDateBeforeValidator validator = new VXLocalDateBeforeValidator(referenceDate);
        // When
        final String errorMessage = validator.getErrorMessage().getDisplay();
        // Then
        Assertions.assertEquals("The date must be before 2023-01-01", errorMessage);
    }
}
