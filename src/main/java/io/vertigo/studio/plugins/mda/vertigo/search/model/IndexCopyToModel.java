package io.vertigo.studio.plugins.mda.vertigo.search.model;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.metamodel.domain.StudioDtField;

public class IndexCopyToModel {
	private final StudioDtField toField;
	private final List<StudioDtField> fromFields;

	public IndexCopyToModel(final StudioDtField toField, final List<StudioDtField> fromFields) {
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
		return fromFields.stream().map(StudioDtField::getName).collect(Collectors.toList());
	}

}
