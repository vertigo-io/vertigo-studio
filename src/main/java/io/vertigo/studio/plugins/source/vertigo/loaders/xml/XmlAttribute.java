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

/**
 * Implémentation de référence.
 * @author pchretien, mlaroche, pforhan
 */
public final class XmlAttribute {
	private final String code;
	private final String label;
	private final boolean persistent;
	private final boolean notNull;
	private final String domain;

	/**
	 * Constructor.
	 */
	public XmlAttribute(final String code, final String label, final boolean persistent, final boolean notNull, final String domain) {
		Assertion.check()
				.isNotBlank(code)
				.isNotBlank(label)
				.isNotBlank(code)
				.isNotBlank(domain, "Le domain du champ '{0}' a été oublié.", label);
		//-----
		this.code = code;
		this.label = label;
		this.persistent = persistent;
		this.notNull = notNull;
		this.domain = domain;
	}

	/**
	 * @return Code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return Libellé.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return Si l'attribut est persistent.
	 */
	public boolean isPersistent() {
		return persistent;
	}

	/**
	 * @return Si l'attribut est obligatoire.
	 */
	public boolean isNotNull() {
		return notNull;
	}

	/**
	 * @return Type de l'attribut au sens poweramc/EA (Nom).
	 */
	public String getDomain() {
		return domain;
	}
}
