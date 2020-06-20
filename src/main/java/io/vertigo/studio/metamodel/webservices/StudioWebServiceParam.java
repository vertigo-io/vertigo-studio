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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Cardinality;

/**
 * WebService param infos :
 * - source type (query, path, body, innerBody or implicit)
 * - name
 * - type (class)
 * - includedField (for DtObjet or DtList)
 * - excludedField (for DtObjet or DtList)
 * - if object kept serverSide
 * - if one time token
 * - specific validators
 *
 * @author npiedeloup
 */
public final class StudioWebServiceParam {

	/**
	 * Parameter's source types.
	 */
	public enum WebServiceParamLocation {
		Query,
		Path,
		Header,
		Body,
		InnerBody;
	}

	private final WebServiceParamLocation paramLocation;
	private final String name;
	private final Type type;
	private final Cardinality cardinality;

	public StudioWebServiceParam(
			final String name,
			final WebServiceParamLocation paramLocation,
			final Type type,
			final Cardinality cardinality) {
		Assertion.check()
				.argNotEmpty(name)
				.notNull(paramLocation)
				.notNull(type)
				.notNull(cardinality);
		//-----
		this.paramLocation = paramLocation;
		this.type = type;
		this.cardinality = cardinality;
		this.name = name;
	}

	/**
	 * @return Parameter's source type
	 */
	public WebServiceParamLocation getParamLocation() {
		return paramLocation;
	}

	/**
	 * @return Parameter name in source
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Parameter class
	 */
	public Class<?> getType() {
		if (type instanceof ParameterizedType) {
			return (Class<?>) ((ParameterizedType) type).getRawType();
		}
		return (Class<?>) type;
	}

	/**
	 * @return generics Type
	 */
	public Type getGenericType() {
		return type;
	}

	/**
	 * @return cardinality
	 */
	public Cardinality getCardinality() {
		return cardinality;
	}

}
