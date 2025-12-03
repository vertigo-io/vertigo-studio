package io.vertigo.vortex.model.types;

/**
 * Centralized property names used by validators.
 */
public final class VxProps {

	private VxProps() {
		// private constructor to prevent instantiation
	}

	public static final String REQUIRED = "required";

	public static final String EXACT_LENGTH = "exactLength";
	public static final String MAX_LENGTH = "maxLength";
	public static final String MIN_LENGTH = "minLength";

	public static final String PATTERN = "pattern";

	public static final String MIN = "min";
	public static final String MAX = "max";
}
