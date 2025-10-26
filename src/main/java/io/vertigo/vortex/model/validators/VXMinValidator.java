package io.vertigo.vortex.model.validators;

import io.vertigo.vortex.model.VXValidator;

public record VXMinValidator(Number min) implements VXValidator {
}
