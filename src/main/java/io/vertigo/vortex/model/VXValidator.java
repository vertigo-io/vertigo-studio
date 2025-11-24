package io.vertigo.vortex.model;

import io.vertigo.core.locale.LocaleMessageText;

/**
 * Represents a validator for a specific data type.
 * Validators are used within DomainTypes to enforce constraints.
 * @param <D> the data type of the value to validate
 * @synthetic
 */
public interface VXValidator<D> {
	/**
	 * Checks if the given value satisfies the constraint.
	 * @param value the value to validate
	 * @return true if the value is valid, false otherwise
	 */
	boolean isValid(D value);

	/**
	 * Returns the error message to be used when the constraint is not satisfied.
	 * @return the error message
	 */
	LocaleMessageText getErrorMessage();
}
