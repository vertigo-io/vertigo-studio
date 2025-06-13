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
package io.vertigo.studio.plugins.generator.vertigo.domain.sql.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.notebook.domain.association.AssociationSimpleSketch;

/**
 * Objet utilisé par FreeMarker.
 *
 * @author pchretien, mlaroche
 */
public final class SqlStudioAssociationSimpleModel {
	private final AssociationSimpleSketch associationSketch;

	/**
	 * Constructeur.
	 * @param associationNode Noeud de l'association à générer
	 */
	public SqlStudioAssociationSimpleModel(final AssociationSimpleSketch associationSketch) {
		Assertion.check().isNotNull(associationSketch);
		//-----
		this.associationSketch = associationSketch;
	}

	/**
	 * @return Association name in CONST_CASE
	 */
	public String getName() {
		return StringUtil.camelToConstCase(associationSketch.getKey().getName());
	}

	/**
	 * @return Association foreign table
	 */
	public String getForeignTable() {
		return StringUtil.camelToConstCase(associationSketch.getForeignAssociationNode().getDtSketch().getLocalName());
	}

	/**
	 * @return Association primary table
	 */
	public String getPrimaryTable() {
		return StringUtil.camelToConstCase(associationSketch.getPrimaryAssociationNode().getDtSketch().getLocalName());
	}

	/**
	 * @return Association FK
	 */
	public String getForeignColumn() {
		return StringUtil.camelToConstCase(associationSketch.getFKField().getName());
	}

	/**
	 * @return Association PK
	 */
	public String getPrimaryIdColumn() {
		return StringUtil.camelToConstCase(associationSketch.getPrimaryAssociationNode().getDtSketch().getIdField().get().getName());
	}
}
