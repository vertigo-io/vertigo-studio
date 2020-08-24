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
package io.vertigo.studio.plugins.source.vertigo.loaders.eaxmi.core;

/**
 * Type d'objets du XMI géré.
 * @author pforhan
 */
public enum EAXmiType {
	/**
	 * Objet Xmi décrivant un Package >>Package.
	 */
	Package("uml:Package"),
	/**
	 * Objet Xmi décrivant une Classe >> DtDefinition.
	 */
	Class("uml:Class"),
	/**
	 * Objet Xmi décrivant un Attibut d'une Classe >> DtField.
	 */
	Attribute("uml:Property"),
	/**
	 * Objet Xmi décrivant une Association >> Association.
	 */
	Association("uml:Association"),
	/**
	 * Tag Xmi pour un élément à traiter.
	 */
	PackageElement("packageElement"),
	/**
	 * Tag Xmi pour un attribut de classe.
	 */
	OwnedAttribute("ownedAttribute"),
	/**
	 * Tag d'extension EA pour un attribut.
	 */
	ClassAttribute("attribute"),
	/**
	 * Tag d'extension EA pour une association.
	 */
	Connector("connector");

	private final String code;

	private EAXmiType(final String code) {
		this.code = code;
	}

	private String getCode() {
		return code;
	}

	static EAXmiType getType(final String code) {
		final EAXmiType type;
		if (Package.getCode().equals(code)) {
			type = Package;
		} else if (Class.getCode().equals(code)) {
			type = Class;
		} else if (Attribute.getCode().equals(code)) {
			type = Attribute;
		} else if (Association.getCode().equals(code)
				|| Connector.getCode().equals(code)) {
			type = Association;
		} else if (ClassAttribute.getCode().equals(code)) {
			type = ClassAttribute;
		} else {
			//rien trouvé
			type = null;
		}
		return type;
	}

	static boolean isNodeByRef(final String code) {
		return Attribute.getCode().equals(code)
				|| Class.getCode().equals(code)
				|| ClassAttribute.getCode().equals(code)
				|| Package.getCode().equals(code)
				|| Connector.getCode().equals(code);
	}

	static boolean isObjet(final String code, final String tagName) {
		return Attribute.getCode().equals(code) && OwnedAttribute.getCode().equals(tagName)
				|| Class.getCode().equals(code)
				|| Package.getCode().equals(code)
				|| Association.getCode().equals(code);
	}

	boolean isAttribute() {
		return this == Attribute;
	}

	boolean isClass() {
		return this == Class
				|| this == Association;
	}
}
