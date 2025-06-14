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
package io.vertigo.studio.plugins.generator.vertigo.webservice.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import io.vertigo.studio.notebook.webservices.WebServiceSketch;
import io.vertigo.studio.notebook.webservices.WebServiceSketchParam;

/**
 * Model for TypeScript generation.
 * @author npiedeloup
 */
public final class WebServiceModelTs {

	private final WebServiceSketch webServiceSketch;
	private final List<WebServiceParamModelTs> webServiceParamModelTsList = new ArrayList<>();
	private final TypeModelTs returnType;

	/**
	 * Constructor.
	 * @param webServiceSketch WebService to generate
	 */
	public WebServiceModelTs(final WebServiceSketch webServiceSketch) {
		this.webServiceSketch = webServiceSketch;

		for (final WebServiceSketchParam wsParam : webServiceSketch.getWebServiceParams()) {
			webServiceParamModelTsList.add(new WebServiceParamModelTs(wsParam));
		}

		if (webServiceSketch.getWebServiceResponseContentOpt().isPresent()) {
			returnType = new TypeModelTs(webServiceSketch.getWebServiceResponseContentOpt().get().getGenericType());

		} else {
			returnType = new TypeModelTs(Void.TYPE);
		}
	}

	/**
	 * @return Method name
	 */
	public String getMethodName() {
		return webServiceSketch.getHumanReadableAction();
	}

	/**
	 * @return List of WebService params
	 */
	public List<WebServiceParamModelTs> getWebServiceParams() {
		return webServiceParamModelTsList;
	}

	/**
	 * @return Js Server Call Method
	 */
	public String getJsServerCallMethod() {
		String verb = webServiceSketch.getVerb().toString().toLowerCase(Locale.ROOT);
		if (isDelete()) {
			verb = "del";
		}
		final StringBuilder method = new StringBuilder(verb);
		if (returnType.isList()) {
			method.append("List");
		}
		if (!returnType.isVoid()) {
			method.append('<').append(returnType.getJsGenericType()).append('>');
		}
		return method.toString();
	}

	/**
	 * @return Extract functional modul from package name
	 */
	public String getFunctionnalPackageName() {
		return webServiceSketch.getModuleName();

	}

	/**
	 * @return if get
	 */
	public boolean isGet() {
		return "GET".equals(webServiceSketch.getVerb().toString());
	}

	/**
	 * @return if delete
	 */
	public boolean isDelete() {
		return "DELETE".equals(webServiceSketch.getVerb().toString());
	}

	/**
	 * @return WebService path
	 */
	public String getPath() {
		final String path = webServiceSketch.getPath();
		return path.replaceAll("\\{(.+?)\\}", "\\${$1}");
	}

	/**
	 * @return Import list
	 */
	public Set<String> getImportList() {
		final Set<String> importList = new HashSet<>();
		for (final WebServiceParamModelTs param : webServiceParamModelTsList) {
			final String importDeclaration = param.getTypeModel().getImportDeclaration();
			if (importDeclaration != null) {
				importList.add(importDeclaration);
			} else if ("facetQueryInput".equals(param.getName())) {
				importList.add("type FacetQueryInput = any;");
			}
		}
		final String importReturnTypeDeclaration = returnType.getImportDeclaration();
		if (importReturnTypeDeclaration != null) {
			importList.add(importReturnTypeDeclaration);
		}

		return importList;
	}
}
