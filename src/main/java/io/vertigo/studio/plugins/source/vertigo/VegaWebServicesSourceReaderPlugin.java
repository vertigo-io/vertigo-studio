/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2022, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.source.vertigo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.core.lang.Selector;
import io.vertigo.core.lang.Selector.ClassConditions;
import io.vertigo.core.util.ClassUtil;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.impl.source.SourceReaderPlugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.webservices.WebServiceSketch;
import io.vertigo.studio.notebook.webservices.WebServiceSketchParam;
import io.vertigo.studio.notebook.webservices.WebServiceSketchParam.WebServiceParamLocation;
import io.vertigo.studio.notebook.webservices.WebServiceSketchResponseContent;
import io.vertigo.studio.source.Source;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.DELETE;
import io.vertigo.vega.webservice.stereotype.Doc;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.HeaderParam;
import io.vertigo.vega.webservice.stereotype.InnerBodyParam;
import io.vertigo.vega.webservice.stereotype.PATCH;
import io.vertigo.vega.webservice.stereotype.POST;
import io.vertigo.vega.webservice.stereotype.PUT;
import io.vertigo.vega.webservice.stereotype.PathParam;
import io.vertigo.vega.webservice.stereotype.PathPrefix;
import io.vertigo.vega.webservice.stereotype.QueryParam;

public final class VegaWebServicesSourceReaderPlugin implements SourceReaderPlugin {

	private static final int NAME_MAX_SIZE = 40;

	@Override
	public Stream<WebServiceSketch> parseResources(final List<Source> sources, final Notebook notebook) {
		Assertion.check()
				.isNotNull(sources)
				.isNotNull(notebook);
		//---
		final List<WebServiceSketch> webServiceSketch = new ArrayList<>();
		for (final Source source : sources) {
			final String resourcePath = source.path();
			if (resourcePath.endsWith(".*")) {
				scanAndAddPackage(resourcePath.substring(0, resourcePath.length() - ".*".length()), webServiceSketch);
			} else {
				final Class<? extends WebServices> webServicesClass = ClassUtil.classForName(resourcePath, WebServices.class);
				webServiceSketch.addAll(scanWebServices(webServicesClass));
			}
		}

		return webServiceSketch.stream();
	}

	private static void scanAndAddPackage(final String packagePath, final List<WebServiceSketch> webServiceSketch) {
		final Collection<Class> webServicesClasses = Selector
				.from(packagePath)
				.filterClasses(ClassConditions.subTypeOf(WebServices.class))
				.findClasses();

		//--Enregistrement des fichiers java annotés
		for (final Class<? extends WebServices> webServicesClass : webServicesClasses) {
			webServiceSketch.addAll(scanWebServices(webServicesClass));
		}
	}

	private static List<WebServiceSketch> scanWebServices(final Class<? extends WebServices> webServicesClass) {
		Assertion.check().isNotNull(webServicesClass);
		//-----
		return Arrays.stream(webServicesClass.getMethods())
				.map(VegaWebServicesSourceReaderPlugin::buildWebServiceSketch)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

	private static Optional<WebServiceSketch> buildWebServiceSketch(final Method method) {
		final PathPrefix pathPrefixAnnotation = method.getDeclaringClass().getAnnotation(PathPrefix.class);
		final String pathPrefix = pathPrefixAnnotation != null ? pathPrefixAnnotation.value() : "";
		for (final Annotation annotation : method.getAnnotations()) {
			if (annotation instanceof GET) {
				return Optional.of(createWebServiceSketch(method, WebServiceSketch.Verb.Get, pathPrefix + ((GET) annotation).value()));
			} else if (annotation instanceof POST) {
				return Optional.of(createWebServiceSketch(method, WebServiceSketch.Verb.Post, pathPrefix + ((POST) annotation).value()));
			} else if (annotation instanceof PUT) {
				return Optional.of(createWebServiceSketch(method, WebServiceSketch.Verb.Put, pathPrefix + ((PUT) annotation).value()));
			} else if (annotation instanceof PATCH) {
				return Optional.of(createWebServiceSketch(method, WebServiceSketch.Verb.Patch, pathPrefix + ((PATCH) annotation).value()));
			} else if (annotation instanceof DELETE) {
				return Optional.of(createWebServiceSketch(method, WebServiceSketch.Verb.Delete, pathPrefix + ((DELETE) annotation).value()));
			}
		}
		return Optional.empty();
	}

	private static WebServiceSketch createWebServiceSketch(final Method method, final WebServiceSketch.Verb verb, final String path) {
		final Type returnType = method.getGenericReturnType();
		final boolean isVoid = void.class.isAssignableFrom(returnType.getClass());
		final Cardinality cardinality = getCardinalityFromType(returnType);

		final String doc = method.isAnnotationPresent(Doc.class) ? method.getAnnotation(Doc.class).value() : "";
		final Type[] paramType = method.getGenericParameterTypes();
		final Annotation[][] parameterAnnotation = method.getParameterAnnotations();

		final List<WebServiceSketchParam> webServiceParams = new ArrayList<>();

		for (int i = 0; i < paramType.length; i++) {
			final Optional<WebServiceSketchParam> webServiceParamOpt = buildWebServiceParam(parameterAnnotation[i], paramType[i]);
			webServiceParamOpt.ifPresent(webServiceParams::add);
		}

		//---
		final String[] fullPackageNameSplited = method.getDeclaringClass().getPackage().toString().split("\\.");
		final String moduleName = fullPackageNameSplited[fullPackageNameSplited.length - 1];
		//---
		return new WebServiceSketch(
				"Ws" + verb + normalizePath(path),
				verb,
				path,
				webServiceParams,
				isVoid ? Optional.empty() : Optional.of(new WebServiceSketchResponseContent(returnType, cardinality)),
				moduleName,
				Optional.of(method.getDeclaringClass().getSimpleName().replaceAll("WebServices", "")),
				method.getName(),
				doc);
	}

	private static Optional<WebServiceSketchParam> buildWebServiceParam(final Annotation[] annotations, final Type paramType) {
		//		 if (DtListState.class.equals(paramType)) {
		//			builder.with(WebServiceParamType.Query, "listState"); //DtListState don't need to be named, it will be retrieve from query
		//		}

		for (final Annotation annotation : annotations) {
			if (annotation instanceof PathParam) {
				return Optional.of(new WebServiceSketchParam(((PathParam) annotation).value(), WebServiceParamLocation.Path, paramType, getCardinalityFromType(paramType)));
			} else if (annotation instanceof QueryParam) {
				return Optional.of(new WebServiceSketchParam(((QueryParam) annotation).value(), WebServiceParamLocation.Query, paramType, getCardinalityFromType(paramType)));
			} else if (annotation instanceof HeaderParam) {
				return Optional.of(new WebServiceSketchParam(((HeaderParam) annotation).value(), WebServiceParamLocation.Header, paramType, getCardinalityFromType(paramType)));
			} else if (annotation instanceof InnerBodyParam) {
				return Optional.of(new WebServiceSketchParam(((InnerBodyParam) annotation).value(), WebServiceParamLocation.InnerBody, paramType, getCardinalityFromType(paramType)));
			}
		}
		// other params, like implicit ones, or purely server side
		return Optional.empty();
	}

	private static Cardinality getCardinalityFromType(final Type returnType) {
		final Cardinality cardinality;
		if (Optional.class.isAssignableFrom(returnType.getClass())) {
			cardinality = Cardinality.OPTIONAL_OR_NULLABLE;
		} else if (List.class.isAssignableFrom(returnType.getClass()) || returnType.getClass().isArray()) {
			cardinality = Cardinality.MANY;
		} else {
			cardinality = Cardinality.ONE;
		}
		return cardinality;
	}

	private static String normalizePath(final String servicePath) {
		final String argsRemovedPath = servicePath
				.replaceAll("_|\\(\\)", "") //remove unsignificative path elements (can't have different routes by _ or ()
				.replaceAll("\\{.*?\\}|\\*", "_");//.*? : reluctant quantifier; remove params which works like wildcards

		//On rend le path plus lisible et compatible DefinitionName
		final String normalizedConstString = argsRemovedPath.toUpperCase(Locale.ROOT)
				.replaceAll("[\\-/]", "_")
				.replaceAll("([0-9]+)([^0-9])", "_$1_$2")
				.replaceAll("([0-9]+)", "_$1")
				.replaceAll("_+", "_");
		final String normalizedString = StringUtil.constToUpperCamelCase(normalizedConstString);
		final String hashcodeAsHex = "$x" + Integer.toHexString(argsRemovedPath.hashCode());
		//On limite sa taille pour avec un nom de définition acceptable
		return normalizedString.substring(0, Math.min(NAME_MAX_SIZE, normalizedString.length())) + hashcodeAsHex;
	}

	@Override
	public Set<String> getHandledSourceTypes() {
		return Set.of("webservice");
	}

}
