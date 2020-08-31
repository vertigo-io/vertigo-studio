package io.vertigo.studio.source.vertigo.data.tasktest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import io.vertigo.core.lang.DataStream;
import io.vertigo.datamodel.smarttype.annotations.FormatterDefault;
import io.vertigo.datamodel.smarttype.annotations.SmartTypeDefinition;
import io.vertigo.datamodel.smarttype.annotations.SmartTypeProperty;

public enum DaoTestSmartTypes {

	@SmartTypeDefinition(Long.class)
	@FormatterDefault
	Id,

	@SmartTypeDefinition(String.class)
	@FormatterDefault
	String,

	@SmartTypeDefinition(Long.class)
	@FormatterDefault
	Long,

	@SmartTypeDefinition(Double.class)
	@FormatterDefault
	Double,

	@SmartTypeDefinition(Integer.class)
	@SmartTypeProperty(property = "indexType", value = "standard:integer")
	@FormatterDefault
	Integer,

	@SmartTypeDefinition(LocalDate.class)
	@FormatterDefault
	Date,

	@SmartTypeDefinition(Instant.class)
	@FormatterDefault
	Instant,

	@SmartTypeDefinition(Boolean.class)
	@FormatterDefault
	Boolean,

	@SmartTypeDefinition(String.class)
	@FormatterDefault
	LibelleLong,

	@SmartTypeDefinition(DataStream.class)
	@FormatterDefault
	Stream,

	@SmartTypeDefinition(String.class)
	@SmartTypeProperty(property = "indexType", value = "text_fr")
	@FormatterDefault
	FullText,

	@SmartTypeDefinition(String.class)
	@SmartTypeProperty(property = "indexType", value = "code")
	@FormatterDefault
	Keyword,

	@SmartTypeDefinition(BigDecimal.class)
	@FormatterDefault
	Conso;

}
