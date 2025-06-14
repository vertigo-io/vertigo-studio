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
package io.vertigo.studio.plugins.source.vertigo.loaders.xml;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.StringUtil;

/**
 * Association.
 * Une association est définie par deux noeuds A et B.
 * Chaque noeud possède des propriétés :
 * - Navigabilité
 * - Cardinalité (appelée multiplicité dans poweramc)
 * - Libellé
 * - Code de l'entité à laquelle est attachée le noeud.
 *
 * D'autre part l'association possède son propre code.
 * @author pchretien, mlaroche, pforhan
 */
public final class XmlAssociation {

	private static final int LAST_SEPARATOR = 6;
	private static final int SECOND_SEPARATOR = 3;
	private static final int FIRST_SEPARATOR = 0;
	private final String code;
	private final String packageName;

	private final String multiplicityA;
	private final String multiplicityB;

	private final boolean navigabilityA;
	private final boolean navigabilityB;

	private final String roleLabelA;
	private final String roleLabelB;

	private final String codeA;
	private final String codeB;

	/**
	 * Constructor.
	 */
	public XmlAssociation(
			final String code,
			final String packageName,
			final String multiplicityA,
			final String multiplicityB,
			final String roleLabelA,
			final String roleLabelB,
			final String codeA,
			final String codeB,
			final boolean navigabilityA,
			final boolean navigabilityB) {
		Assertion.check()
				.isNotBlank(code)
				/*packageName can be null*/
				.isNotBlank(multiplicityA)
				.isNotBlank(multiplicityB)
				.isNotBlank(roleLabelA)
				.isNotBlank(roleLabelB)
				.isNotBlank(codeA)
				.isNotBlank(codeB);
		//-----
		this.code = code;
		this.packageName = packageName;

		// On gère le cas du 1 tout seul.
		if ("1".equals(multiplicityA)) {
			this.multiplicityA = "1..1";
		} else {
			this.multiplicityA = multiplicityA;
		}
		if ("1".equals(multiplicityB)) {
			this.multiplicityB = "1..1";
		} else {
			this.multiplicityB = multiplicityB;
		}
		this.roleLabelA = roleLabelA;
		this.roleLabelB = roleLabelB;

		this.codeA = codeA;
		this.codeB = codeB;

		this.navigabilityA = navigabilityA;
		this.navigabilityB = navigabilityB;
	}

	/**
	 * Formatage du code : AaaBbb{CodeName}
	 * Aaa = Trois première lettre du code de A
	 * Bbb = Trois première lettre du code de B
	 * Le nom défini par l'utilisateur est facultatif.
	 *
	 * @return Code de l'association
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Le code suivant est déduit du code.
	 * @return Nom de l'association défini par l'utilisateur. (Peut être null)
	 */
	public String getCodeName() {
		if (code.length() > 6 //we need at least 2 chars after the 2 trigrams
				&& StringUtil.isUpperCamelCase(code.substring(FIRST_SEPARATOR, FIRST_SEPARATOR + 2))
				&& StringUtil.isUpperCamelCase(code.substring(SECOND_SEPARATOR, SECOND_SEPARATOR + 2))) {

			//Assert if codeName too short
			if (!(code.length() > 7 && StringUtil.isUpperCamelCase(code.substring(LAST_SEPARATOR, LAST_SEPARATOR + 2))
					|| Character.isDigit(code.charAt(LAST_SEPARATOR)))) {
				throw new IllegalArgumentException("For association '" + code + "' the code name must be a number or at least 2 upperCamelCase chars."
						+ " The code must be build like : {Trigram Table1}{Trigram Table2}{Association code}."
						+ " Examples : DosUtiEmmeteur, DosUtiDestinataire, DosDosParent, ...");
			}
			return code.substring(6);
		}
		return null;
	}

	/**
	 * @return Nom du package.
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @return Multiplicité de A.
	 */
	public String getMultiplicityA() {
		return multiplicityA;
	}

	/**
	 * @return Multiplicité de B.
	 */
	public String getMultiplicityB() {
		return multiplicityB;
	}

	/**
	 * @return Navigabilité de A.
	 */
	public boolean isNavigableA() {
		return navigabilityA;
	}

	/**
	 * @return Navigabilité de B.
	 */
	public boolean isNavigableB() {
		return navigabilityB;
	}

	/**
	 * @return Libellé du noeud A.
	 */
	public String getRoleLabelA() {
		return roleLabelA;
	}

	/**
	 * @return Libellé du noeud B.
	 */
	public String getRoleLabelB() {
		return roleLabelB;
	}

	/**
	 * @return Code de l'entité A participant à l'association
	 *
	 */
	public String getCodeA() {
		return codeA;
	}

	/**
	 * @return Code de l'entité B participant à l'association.
	 */
	public String getCodeB() {
		return codeB;
	}
}
