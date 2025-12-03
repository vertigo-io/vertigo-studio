package io.vertigo.vortex.model.types.validators.time;

import java.time.Instant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VXInstantAfterValidatorTest {

    @Test
    void shouldValidateInstantAfter() {
        // Given
        final Instant referenceInstant = Instant.now();
        final VXInstantAfterValidator validator = new VXInstantAfterValidator(referenceInstant);
        final Instant value = referenceInstant.plusSeconds(1);
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldNotValidateInstantBefore() {
        // Given
        final Instant referenceInstant = Instant.now();
        final VXInstantAfterValidator validator = new VXInstantAfterValidator(referenceInstant);
        final Instant value = referenceInstant.minusSeconds(1);
        // When
        Assertions.assertFalse(validator.isValid(value));
    }

    @Test
    void shouldNotValidateInstantEqual() {
        // Given
        final Instant referenceInstant = Instant.now();
        final VXInstantAfterValidator validator = new VXInstantAfterValidator(referenceInstant);
        final Instant value = referenceInstant;
        // When
        Assertions.assertFalse(validator.isValid(value));
    }

    @Test
    void shouldValidateNullInstant() {
        // Given
        final Instant referenceInstant = Instant.now();
        final VXInstantAfterValidator validator = new VXInstantAfterValidator(referenceInstant);
        final Instant value = null;
        // When
        Assertions.assertTrue(validator.isValid(value));
        // Then - no exception is thrown
    }

    @Test
    void shouldReturnCorrectErrorMessage() {
        // Given
        final Instant referenceInstant = Instant.now();
        final VXInstantAfterValidator validator = new VXInstantAfterValidator(referenceInstant);
        // When
        final String errorMessage = validator.getErrorMessage().getDisplay();
        // Then
        Assertions.assertEquals("The instant must be after " + referenceInstant, errorMessage);
    }
}
