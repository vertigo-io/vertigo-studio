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
package io.vertigo.studio.notebook.domain.association;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.SkecthPrefix;
import io.vertigo.studio.notebook.SketchKey;

/**
 * Définition d'une association NN.
 * @author  jcassignol, pchretien
 */
@SkecthPrefix("Ann")
public final class AssociationNNSketch extends AssociationSketch {
	private final String tableName;

	/**
	 * Constructeur d'une association n-n.
	 * @param name the name of the sketch
	 * @param tableName Nom de la table
	 * @param associationNodeA Noeud A
	 * @param associationNodeB Noeud B
	 */
	public AssociationNNSketch(
			final SketchKey key,
			final String tableName,
			final AssociationSketchNode associationNodeA,
			final AssociationSketchNode associationNodeB) {
		super(key, associationNodeA, associationNodeB);
		//-----
		Assertion.check().isNotNull(tableName);
		this.tableName = tableName;
	}

	/**
	 * @return Nom de la table porteuse de la relation NN
	 */
	public String getTableName() {
		return tableName;
	}
}
