/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2019, Vertigo.io, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
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
package io.vertigo.studio.plugins.mda.vertigo.domain.sql.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.domain.association.AssociationNNSketch;

/**
 * Objet utilisé par FreeMarker.
 *
 * @author pchretien, mlaroche
 */
public final class SqlStudioAssociationNNModel {
	private final AssociationNNSketch associationSketch;

	/**
	 * Constructeur.
	 * @param associationNode Noeud de l'association à générer
	 */
	public SqlStudioAssociationNNModel(final AssociationNNSketch associationSketch) {
		Assertion.check().isNotNull(associationSketch);
		//-----
		this.associationSketch = associationSketch;
	}

	/**
	 * @return Association name
	 */
	public String getName() {
		return StringUtil.camelToConstCase(associationSketch.getName());
	}

	/**
	 * @return Association NN table
	 */
	public String getTableName() {
		return associationSketch.getTableName();
	}

	/**
	 * @return Association nodeA table
	 */
	public String getNodeATableName() {
		return StringUtil.camelToConstCase(associationSketch.getAssociationNodeA().getDtSketch().getLocalName());
	}

	/**
	 * @return Association nodeA Id column name
	 */
	public String getNodeAPKName() {
		return StringUtil.camelToConstCase(associationSketch.getAssociationNodeA().getDtSketch().getIdField().get().getName());
	}

	/**
	 * @return Association nodeA Id domain
	 */
	public DomainSketch getNodeAPKDomain() {
		return associationSketch.getAssociationNodeA().getDtSketch().getIdField().get().getDomain();
	}

	/**
	 * @return Association nodeB table
	 */
	public String getNodeBTableName() {
		return StringUtil.camelToConstCase(associationSketch.getAssociationNodeB().getDtSketch().getLocalName());
	}

	/**
	 * @return Association nodeB Id column name
	 */
	public String getNodeBPKName() {
		return StringUtil.camelToConstCase(associationSketch.getAssociationNodeB().getDtSketch().getIdField().get().getName());
	}

	/**
	 * @return Association nodeB Id domain
	 */
	public DomainSketch getNodeBPKDomain() {
		return associationSketch.getAssociationNodeB().getDtSketch().getIdField().get().getDomain();
	}

}
