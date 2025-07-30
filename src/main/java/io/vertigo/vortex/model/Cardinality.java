package io.vertigo.vortex.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VSystemException;

public enum Cardinality {
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

	public static Cardinality fromSymbol(final String sCardinality) {
		Assertion.check().isNotBlank(sCardinality);
		//---
		return switch (sCardinality) {
			case "?" -> NULLABLE;
			case "1" -> ONE;
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
