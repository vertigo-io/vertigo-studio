package io.vertigo.vortex.model.library;

import java.time.temporal.Temporal;

import io.vertigo.vortex.model.library.types.VXPropertyKey;

/**
 * Centralized property names used by validators.
 */
public final class VXProps {

	private VXProps() {
		// private constructor to prevent instantiation
	}

	public static final VXPropertyKey<Integer> EXACT_LENGTH = VXPropertyKey.of("exactLength", Integer.class);
	public static final VXPropertyKey<Integer> MAX_LENGTH = VXPropertyKey.of("maxLength", Integer.class);
	public static final VXPropertyKey<Integer> MIN_LENGTH = VXPropertyKey.of("minLength", Integer.class);

	public static final VXPropertyKey<String> PATTERN = VXPropertyKey.of("pattern", String.class);

	public static final VXPropertyKey<String> STARTS_WITH = VXPropertyKey.of("startsWith", String.class);
	public static final VXPropertyKey<String> ENDS_WITH = VXPropertyKey.of("endsWith", String.class);
	public static final VXPropertyKey<String> CONTAINS = VXPropertyKey.of("contains", String.class);

	public static final VXPropertyKey<Boolean> EMAIL = VXPropertyKey.of("email", Boolean.class);
	public static final VXPropertyKey<Boolean> URL = VXPropertyKey.of("url", Boolean.class);
	public static final VXPropertyKey<Boolean> JSON = VXPropertyKey.of("json", Boolean.class);

	public static final VXPropertyKey<Number> MIN = VXPropertyKey.of("min", Number.class);
	public static final VXPropertyKey<Number> MAX = VXPropertyKey.of("max", Number.class);

	public static final VXPropertyKey<Temporal> AFTER = VXPropertyKey.of("after", Temporal.class);
	public static final VXPropertyKey<Temporal> BEFORE = VXPropertyKey.of("before", Temporal.class);
	public static final VXPropertyKey<Boolean> FUTURE = VXPropertyKey.of("future", Boolean.class);
	public static final VXPropertyKey<Boolean> PAST = VXPropertyKey.of("past", Boolean.class);
}
