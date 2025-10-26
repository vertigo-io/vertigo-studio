package io.vertigo.vortex.model.validators;

import io.vertigo.vortex.model.VXValidator;

public record VXMaxValidator(Number max) implements VXValidator {
}
