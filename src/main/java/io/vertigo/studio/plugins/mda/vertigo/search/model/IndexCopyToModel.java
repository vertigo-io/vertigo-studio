package io.vertigo.studio.plugins.mda.vertigo.search.model;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DtSketchField;

public class IndexCopyToModel {
	private final DtSketchField toField;
	private final List<DtSketchField> fromFields;

	public IndexCopyToModel(final DtSketchField toField, final List<DtSketchField> fromFields) {
		Assertion.check()
				.isNotNull(toField)
				.isNotNull(fromFields);
		//---
		this.toField = toField;
		this.fromFields = fromFields;
	}

	public String getTo() {
		return toField.getName();
	}

	public List<String> getFrom() {
		return fromFields.stream().map(DtSketchField::getName).collect(Collectors.toList());
	}

}
