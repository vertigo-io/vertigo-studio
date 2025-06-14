/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2025, Vertigo.io, team@vertigo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertigo.studio.plugins.generator.vertigo.task.test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.vertigo.core.lang.DataStream;
import io.vertigo.core.lang.VSystemException;
import io.vertigo.datamodel.data.definitions.DataDefinition;
import io.vertigo.datamodel.data.definitions.DataField.FieldType;
import io.vertigo.datamodel.data.model.DataObject;
import io.vertigo.datamodel.data.model.DtList;
import io.vertigo.datamodel.data.util.DataModelUtil;

/**
 * Basic dummy values generator. (One possible value for each type)
 *
 * @author sezratty, mlaroche
 *
 */
public class TaskTestDummyGeneratorBasic implements TaskTestDummyGenerator {

	@Override
	public <T> T dum(final Class<T> type) {
		if (DataObject.class.isAssignableFrom(type)) {
			//we are a dtObject
			return (T) dum(DataModelUtil.findDataDefinition((Class<DataObject>) type));
		} else if (Integer.class.equals(type) || int.class.equals(type)) {
			return (T) Integer.valueOf(1);
		} else if (Double.class.equals(type) || double.class.equals(type)) {
			return (T) Double.valueOf(1.00);
		} else if (Boolean.class.equals(type) || boolean.class.equals(type)) {
			return (T) Boolean.TRUE;
		} else if (String.class.equals(type)) {
			return (T) "String";
		} else if (Date.class.equals(type)) {
			return (T) new Date();
		} else if (LocalDate.class.equals(type)) {
			return (T) LocalDate.now();
		} else if (Instant.class.equals(type)) {
			return (T) Instant.now();
		} else if (java.math.BigDecimal.class.equals(type)) {
			return (T) new BigDecimal(10);
		} else if (Long.class.equals(type) || long.class.equals(type)) {
			return (T) Long.valueOf(1L);
		} else if (DataStream.class.equals(type)) {
			return null; //TODO better
		} else {
			throw new VSystemException("Type  : '{0}' is not supported for generating dummy values", type.getCanonicalName());
		}
	}

	@Override
	public <T> List<T> dumList(final Class<T> clazz) {
		return Collections.singletonList(dum(clazz));

	}

	@Override
	public <D extends DataObject> DtList<D> dumDtList(final Class<D> dtoClass) {
		return DtList.of(dum(dtoClass));
	}

	/* (non-Javadoc)
	 * @see io.vertigo.studio.plugins.mda.task.test.TaskTestDummyGenerator#dumNew(java.lang.Class)
	 */
	@Override
	public <D extends DataObject> D dumNew(final Class<D> dtoClass) {
		final DataDefinition dtDefinition = DataModelUtil.findDataDefinition(dtoClass);
		final D object = dum(dtoClass);
		dtDefinition.getIdField()
				.ifPresent(idField -> idField.getDataAccessor().setValue(object, null));// we make it pristine
		return object;

	}

	private DataObject dum(final DataDefinition def) {
		/* Créé une instance du dto. */
		final DataObject dto = DataModelUtil.createDataObject(def);
		/* Parcourt les champs */
		def.getFields()
				.stream()
				.filter(dtField -> dtField.getType() != FieldType.COMPUTED)// we don't treat computed field (no setter)
				.forEach(dtField -> {
					final Class javaClass = dtField.smartTypeDefinition().getJavaClass();
					final Object value;
					if (dtField.cardinality().hasMany()) {
						if (dtField.smartTypeDefinition().getScope().isDataType()) {
							value = dumDtList(javaClass);
						} else {
							value = dumList(javaClass);
						}
					} else {
						value = dum(javaClass);
					}
					dtField.getDataAccessor().setValue(dto, value);
				});
		/* Retourne l'instance factice */
		return dto;
	}

}
