package io.vertigo.studio.source.vertigo.data.tasktest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import io.vertigo.basics.formatter.FormatterDefault;
import io.vertigo.core.lang.DataStream;
import io.vertigo.datamodel.smarttype.annotations.Formatter;
import io.vertigo.datamodel.smarttype.annotations.SmartTypeDefinition;
import io.vertigo.datamodel.smarttype.annotations.SmartTypeProperty;

public enum DaoTestSmartTypes {

	@SmartTypeDefinition(Long.class)
	@Formatter(clazz = FormatterDefault.class)
	Id,

	@SmartTypeDefinition(String.class)
	@Formatter(clazz = FormatterDefault.class)
	String,

	@SmartTypeDefinition(Long.class)
	@Formatter(clazz = FormatterDefault.class)
	Long,

	@SmartTypeDefinition(Double.class)
	@Formatter(clazz = FormatterDefault.class)
	Double,

	@SmartTypeDefinition(Integer.class)
	@SmartTypeProperty(property = "indexType", value = "standard:integer")
	@Formatter(clazz = FormatterDefault.class)
	Integer,

	@SmartTypeDefinition(LocalDate.class)
	@Formatter(clazz = FormatterDefault.class)
	Date,

	@SmartTypeDefinition(Instant.class)
	@Formatter(clazz = FormatterDefault.class)
	Instant,

	@SmartTypeDefinition(Boolean.class)
	@Formatter(clazz = FormatterDefault.class)
	Boolean,

	@SmartTypeDefinition(String.class)
	@Formatter(clazz = FormatterDefault.class)
	LibelleLong,

	@SmartTypeDefinition(DataStream.class)
	@Formatter(clazz = FormatterDefault.class)
	Stream,

	@SmartTypeDefinition(String.class)
	@SmartTypeProperty(property = "indexType", value = "text_fr")
	@Formatter(clazz = FormatterDefault.class)
	FullText,

	@SmartTypeDefinition(String.class)
	@SmartTypeProperty(property = "indexType", value = "code")
	@Formatter(clazz = FormatterDefault.class)
	Keyword,

	@SmartTypeDefinition(BigDecimal.class)
	@Formatter(clazz = FormatterDefault.class)
	Conso;

}
