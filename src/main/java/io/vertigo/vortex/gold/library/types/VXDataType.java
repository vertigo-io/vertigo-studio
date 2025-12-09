package io.vertigo.vortex.gold.library.types;

public enum VXDataType {
	//---can be multiple => []
	String,
	Long,
	Integer,
	Double,
	BigDecimal,
	Boolean,
	LocalDate,
	Instant,
	//---Should not be multiple
	Text,
	Json,
}
