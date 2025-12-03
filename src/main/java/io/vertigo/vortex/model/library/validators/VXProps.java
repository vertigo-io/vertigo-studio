package io.vertigo.vortex.model.library.validators;

import java.time.temporal.Temporal;

import io.vertigo.vortex.model.types.VXPropertyKey;

/**
 * Centralized property names used by validators.
 */
public final class VXProps {

	private VXProps() {
		// private constructor to prevent instantiation
	}

	public static final VXPropertyKey<Boolean> REQUIRED = new VXPropertyKey<>("required", Boolean.class);

	public static final VXPropertyKey<Integer> EXACT_LENGTH = new VXPropertyKey<>("exactLength", Integer.class);
	public static final VXPropertyKey<Integer> MAX_LENGTH = new VXPropertyKey<>("maxLength", Integer.class);
	public static final VXPropertyKey<Integer> MIN_LENGTH = new VXPropertyKey<>("minLength", Integer.class);

	public static final VXPropertyKey<String> PATTERN = new VXPropertyKey<>("pattern", String.class);

	public static final VXPropertyKey<String> STARTS_WITH = new VXPropertyKey<>("startsWith", String.class);
	public static final VXPropertyKey<String> ENDS_WITH = new VXPropertyKey<>("endsWith", String.class);
	public static final VXPropertyKey<String> CONTAINS = new VXPropertyKey<>("contains", String.class);

	public static final VXPropertyKey<Boolean> EMAIL = new VXPropertyKey<>("email", Boolean.class);
	public static final VXPropertyKey<Boolean> URL = new VXPropertyKey<>("url", Boolean.class);
	public static final VXPropertyKey<Boolean> JSON = new VXPropertyKey<>("json", Boolean.class);

	public static final VXPropertyKey<Number> MIN = new VXPropertyKey<>("min", Number.class);
	public static final VXPropertyKey<Number> MAX = new VXPropertyKey<>("max", Number.class);

	public static final VXPropertyKey<Temporal> AFTER = new VXPropertyKey<>("after", Temporal.class);
	public static final VXPropertyKey<Temporal> BEFORE = new VXPropertyKey<>("before", Temporal.class);
	public static final VXPropertyKey<Boolean> FUTURE = new VXPropertyKey<>("future", Boolean.class);
	public static final VXPropertyKey<Boolean> PAST = new VXPropertyKey<>("past", Boolean.class);
}
