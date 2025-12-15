package io.vertigo.vortex.model.types.validators.time;

import java.time.Instant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.vortex.notebook.library.validators.time.VXInstantBeforeValidator;

public class VXInstantBeforeValidatorTest {

    @Test
    void shouldValidateInstantBefore() {
        // Given
        final Instant referenceInstant = Instant.now();
        final VXInstantBeforeValidator validator = new VXInstantBeforeValidator(referenceInstant);
        final Instant value = referenceInstant.minusSeconds(1);
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldNotValidateInstantAfter() {
        // Given
        final Instant referenceInstant = Instant.now();
        final VXInstantBeforeValidator validator = new VXInstantBeforeValidator(referenceInstant);
        final Instant value = referenceInstant.plusSeconds(1);
        // When
        Assertions.assertFalse(validator.isValid(value));
    }

    @Test
    void shouldNotValidateInstantEqual() {
        // Given
        final Instant referenceInstant = Instant.now();
        final VXInstantBeforeValidator validator = new VXInstantBeforeValidator(referenceInstant);
        final Instant value = referenceInstant;
        // When
        Assertions.assertFalse(validator.isValid(value));
    }

    @Test
    void shouldValidateNullInstant() {
        // Given
        final Instant referenceInstant = Instant.now();
        final VXInstantBeforeValidator validator = new VXInstantBeforeValidator(referenceInstant);
        final Instant value = null;
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldReturnCorrectErrorMessage() {
        // Given
        final Instant referenceInstant = Instant.now();
        final VXInstantBeforeValidator validator = new VXInstantBeforeValidator(referenceInstant);
        // When
        final String errorMessage = validator.getErrorMessage().getDisplay();
        // Then
        Assertions.assertEquals("The instant must be before " + referenceInstant, errorMessage);
    }
}
