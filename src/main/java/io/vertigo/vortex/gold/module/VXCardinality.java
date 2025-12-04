package io.vertigo.vortex.gold.module;

import io.vertigo.core.lang.VSystemException;

public enum VXCardinality {
	/**
	 * Used for nullable references.
	 * Symbol: ?
	 */
	NULLABLE,
	/**
	 * Symbol: 1
	 */
	ONE,
	/**
	 * Symbol: *
	 */
	MANY;

	public static VXCardinality fromSymbol(final String sCardinality) {
		// If cardinality is null, defaults to '1'
		return switch (sCardinality) {
			case null -> ONE;
			case "1" -> ONE;
			case "?" -> NULLABLE;
			case "*" -> MANY;
			default -> throw new VSystemException("Unknown cardinality symbol : '{0}'. Supported cardinalities are '?' for optional, '1' for one and '*' for many ", sCardinality);
		};
	}

	/**
	 * Converts cardinality to standard notation symbol.
	 * @return Symbol (?, 1, or *)
	 */
	public String toSymbol() {
		return switch (this) {
			case NULLABLE -> "?";
			case ONE -> "1";
			case MANY -> "*";
		};
	}

	@Override
	public String toString() {
		return toSymbol();
	}
}
