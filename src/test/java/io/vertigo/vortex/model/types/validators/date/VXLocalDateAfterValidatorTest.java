package io.vertigo.vortex.model.types.validators.date;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.vortex.gold.library.validators.date.VXLocalDateAfterValidator;

public class VXLocalDateAfterValidatorTest {

    @Test
    void shouldValidateDateAfter() {
        // Given
        final LocalDate referenceDate = LocalDate.of(2023, 1, 1);
        final VXLocalDateAfterValidator validator = new VXLocalDateAfterValidator(referenceDate);
        final LocalDate value = LocalDate.of(2023, 1, 2);
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldNotValidateDateBefore() {
        // Given
        final LocalDate referenceDate = LocalDate.of(2023, 1, 1);
        final VXLocalDateAfterValidator validator = new VXLocalDateAfterValidator(referenceDate);
        final LocalDate value = LocalDate.of(2022, 12, 31);
        // When
        Assertions.assertFalse(validator.isValid(value));
    }

    @Test
    void shouldNotValidateDateEqual() {
        // Given
        final LocalDate referenceDate = LocalDate.of(2023, 1, 1);
        final VXLocalDateAfterValidator validator = new VXLocalDateAfterValidator(referenceDate);
        final LocalDate value = LocalDate.of(2023, 1, 1);
        // When
        Assertions.assertFalse(validator.isValid(value));
    }

    @Test
    void shouldValidateNullDate() {
        // Given
        final LocalDate referenceDate = LocalDate.of(2023, 1, 1);
        final VXLocalDateAfterValidator validator = new VXLocalDateAfterValidator(referenceDate);
        final LocalDate value = null;
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldReturnCorrectErrorMessage() {
        // Given
        final LocalDate referenceDate = LocalDate.of(2023, 1, 1);
        final VXLocalDateAfterValidator validator = new VXLocalDateAfterValidator(referenceDate);
        // When
        final String errorMessage = validator.getErrorMessage().getDisplay();
        // Then
        Assertions.assertEquals("The date must be after 2023-01-01", errorMessage);
    }
}
