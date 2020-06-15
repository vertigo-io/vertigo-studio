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
package io.vertigo.studio.metamodel.webservices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.definition.Definition;
import io.vertigo.core.node.definition.DefinitionPrefix;

/**
 * Web service definition.
 * @author npiedeloup
 */
@DefinitionPrefix("StWs")
public final class StudioWebServiceDefinition implements Definition {

	/**
	 * HTTP Verb supported.
	 */
	public enum Verb {
		Get, Post, Put, Patch, Delete,
	}

	private final String name;
	private final Verb verb;
	private final String path;
	private final List<StudioWebServiceParam> webServiceParams;
	private final Optional<StudioWebServiceResponseContent> webServiceResponseContentOpt;

	private final String moduleName;
	private final Optional<String> groupNameOpt;
	private final String humanReadableAction;
	private final String doc;

	/**
	 * Constructor.
	 */
	public StudioWebServiceDefinition(
			final String name,
			final Verb verb,
			final String path,
			final List<StudioWebServiceParam> webServiceParams,
			final Optional<StudioWebServiceResponseContent> webServiceResponseContentOpt,
			final String moduleName,
			final Optional<String> groupNameOpt,
			final String humanReadableAction,
			final String doc) {
		Assertion.check()
				.argNotEmpty(name)
				.notNull(verb)
				.argNotEmpty(path)
				.notNull(webServiceParams)
				.notNull(webServiceResponseContentOpt)
				.argNotEmpty(moduleName)
				.notNull(groupNameOpt)
				.argNotEmpty(humanReadableAction)
				.notNull(doc); //doc can be empty
		//-----
		this.name = name;
		this.verb = verb;
		this.path = path;
		this.webServiceParams = Collections.unmodifiableList(new ArrayList<>(webServiceParams));
		this.webServiceResponseContentOpt = webServiceResponseContentOpt;
		this.moduleName = moduleName;
		this.groupNameOpt = groupNameOpt;
		this.humanReadableAction = humanReadableAction;
		this.doc = doc;
	}

	/**
	 * @return name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return verb
	 */
	public Verb getVerb() {
		return verb;
	}

	/**
	 * @return webServiceParams
	 */
	public List<StudioWebServiceParam> getWebServiceParams() {
		return webServiceParams;
	}

	public Optional<StudioWebServiceResponseContent> getWebServiceResponseContentOpt() {
		return webServiceResponseContentOpt;
	}

	public String getModuleName() {
		return moduleName;
	}

	public Optional<String> getGroupNameOpt() {
		return groupNameOpt;
	}

	public String getHumanReadableAction() {
		return humanReadableAction;
	}

	/**
	 * @return doc
	 */
	public String getDoc() {
		return doc;
	}

}
