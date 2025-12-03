package io.vertigo.vortex.model.types;

public enum VXDataType {
	//---can be multiple => []
	String,
	Long,
	Integer,
	Double,
	Boolean,
	//---Should not be multiple
	Text,
	Json,
	Entity
}
