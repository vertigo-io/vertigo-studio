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
package io.vertigo.studio.plugins.mda.vertigo.webservice.model;

import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.notebook.webservices.WebServiceSketchParam;
import io.vertigo.studio.notebook.webservices.WebServiceSketchParam.WebServiceParamLocation;

/**
 * FreeMarker Model of WebServiceParamModel.
 * @author npiedeloup
 */
public final class WebServiceParamModelTs {

	private final WebServiceSketchParam webServiceParam;
	private final TypeModelTs typeModelTs;

	/**
	 * Constructor.
	 * @param webServiceParam web service param
	 */
	public WebServiceParamModelTs(final WebServiceSketchParam webServiceParam) {
		this.webServiceParam = webServiceParam;
		typeModelTs = new TypeModelTs(webServiceParam.getGenericType());
	}

	/**
	 * @return param type
	 */
	public TypeModelTs getTypeModel() {
		return typeModelTs;
	}

	/**
	 * @return param name
	 */
	public String getName() {
		String paramName = webServiceParam.getName();
		if (webServiceParam.getParamLocation() == WebServiceParamLocation.Body) {
			paramName = webServiceParam.getType().getSimpleName();
		}
		paramName = paramName.replaceAll("\\W", "");
		return StringUtil.first2LowerCase(paramName);
	}
}
