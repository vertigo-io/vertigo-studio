/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2020, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.notebook.webservices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.AbstractSketch;
import io.vertigo.studio.notebook.SkecthPrefix;

/**
 * Web service Sketch.
 * @author npiedeloup
 */
@SkecthPrefix(WebServiceSketch.PREFIX)
public final class WebServiceSketch extends AbstractSketch {
	public static final String PREFIX = "Ws";

	/**
	 * HTTP Verb supported.
	 */
	public enum Verb {
		Get,
		Post,
		Put,
		Patch,
		Delete,
	}

	private final Verb verb;
	private final String path;
	private final List<WebServiceSketchParam> webServiceParams;
	private final Optional<WebServiceSketchResponseContent> webServiceResponseContentOpt;

	private final String moduleName;
	private final Optional<String> groupNameOpt;
	private final String humanReadableAction;
	private final String doc;

	/**
	 * Constructor.
	 */
	public WebServiceSketch(
			final String name,
			final Verb verb,
			final String path,
			final List<WebServiceSketchParam> webServiceParams,
			final Optional<WebServiceSketchResponseContent> webServiceResponseContentOpt,
			final String moduleName,
			final Optional<String> groupNameOpt,
			final String humanReadableAction,
			final String doc) {
		super(name);
		//---
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(verb)
				.isNotBlank(path)
				.isNotNull(webServiceParams)
				.isNotNull(webServiceResponseContentOpt)
				.isNotBlank(moduleName)
				.isNotNull(groupNameOpt)
				.isNotBlank(humanReadableAction)
				.isNotNull(doc); //doc can be empty
		//-----
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
	public List<WebServiceSketchParam> getWebServiceParams() {
		return webServiceParams;
	}

	public Optional<WebServiceSketchResponseContent> getWebServiceResponseContentOpt() {
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
