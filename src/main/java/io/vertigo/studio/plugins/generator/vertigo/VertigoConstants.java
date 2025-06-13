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
package io.vertigo.studio.plugins.generator.vertigo;

import io.vertigo.core.lang.Assertion;

public class VertigoConstants {

	public static final String DEFAULT_DATA_SPACE = "main";

	public enum VertigoClassNames {

		DtList("io.vertigo.datamodel.data.model.DtList"), //
		Entity("io.vertigo.datamodel.data.model.Entity"), //
		DtObject("io.vertigo.datamodel.data.model.DataObject"), //
		DtMasterData("io.vertigo.datamodel.data.model.DtMasterData"), //
		DtStaticMasterData("io.vertigo.datamodel.data.model.DtStaticMasterData"), //
		KeyConcept("io.vertigo.datamodel.data.model.KeyConcept"), //
		Fragment("io.vertigo.datamodel.data.model.Fragment"), //
		FragmentAnnotation("io.vertigo.datamodel.data.stereotype.Fragment"), //
		AnnotationDataSpace("io.vertigo.datamodel.data.stereotype.DataSpace"), //
		AnnotationForeignKey("io.vertigo.datamodel.data.stereotype.ForeignKey"), //
		AnnotationAssociation("io.vertigo.datamodel.data.stereotype.Association"), //
		AnnotationAssociationNN("io.vertigo.datamodel.data.stereotype.AssociationNN");

		private final String packageName;
		private final String simpleName;
		private final String canonicalName;

		VertigoClassNames(final String canonicalName) {
			Assertion.check().isNotBlank(canonicalName);
			//---
			this.canonicalName = canonicalName;
			final int lastDot = canonicalName.lastIndexOf('.');
			Assertion.check().isTrue(lastDot > 0, "A cananical class name is required, '{0}' does not contain package name", canonicalName);
			simpleName = canonicalName.substring(lastDot + 1);
			packageName = canonicalName.substring(0, lastDot);
		}

		public String getPackageName() {
			return packageName;
		}

		public String getClassName() {
			return canonicalName;
		}

		public String getSimpleName() {
			return simpleName;
		}

	}

	public enum VertigoDefinitionPrefix {

		DtDefinition("Dt"), //
		FacetedQueryDefiniton("Qry"), //
		SearchIndexDefinition("Idx"), //
		Authorization("Atz");

		private final String prefix;

		VertigoDefinitionPrefix(final String prefix) {
			Assertion.check().isNotBlank(prefix);
			//---
			this.prefix = prefix;
		}

		public String getPrefix() {
			return prefix;
		}

	}
}
