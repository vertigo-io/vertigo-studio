package io.vertigo.studio.plugins.metamodel.vertigo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.core.node.definition.DefinitionSupplier;
import io.vertigo.core.util.ClassUtil;
import io.vertigo.core.util.Selector;
import io.vertigo.core.util.Selector.ClassConditions;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.impl.metamodel.MetamodelResourceParserPlugin;
import io.vertigo.studio.metamodel.MetamodelRepository;
import io.vertigo.studio.metamodel.MetamodelResource;
import io.vertigo.studio.metamodel.webservices.StudioWebServiceDefinition;
import io.vertigo.studio.metamodel.webservices.StudioWebServiceParam;
import io.vertigo.studio.metamodel.webservices.StudioWebServiceParam.WebServiceParamLocation;
import io.vertigo.studio.metamodel.webservices.StudioWebServiceResponseContent;
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

public class VegaWebServicesResourceParserPlugin implements MetamodelResourceParserPlugin {

	private static final int NAME_MAX_SIZE = 40;

	@Override
	public List<DefinitionSupplier> parseResources(final List<MetamodelResource> resources, final MetamodelRepository metamodelRepository) {
		final List<StudioWebServiceDefinition> webServiceDefinitions = new ArrayList<>();
		for (final MetamodelResource resource : resources) {
			final String resourcePath = resource.getPath();
			if (resourcePath.endsWith(".*")) {
				scanAndAddPackage(resourcePath.substring(0, resourcePath.length() - ".*".length()), webServiceDefinitions);
			} else {
				final Class<? extends WebServices> webServicesClass = ClassUtil.classForName(resourcePath, WebServices.class);
				webServiceDefinitions.addAll(scanWebServices(webServicesClass));
			}
		}

		return webServiceDefinitions.stream().map(webserviceDefinition -> (DefinitionSupplier) dS -> webserviceDefinition).collect(Collectors.toList());
	}

	private static void scanAndAddPackage(final String packagePath, final List<StudioWebServiceDefinition> webServiceDefinitions) {
		final Collection<Class> webServicesClasses = new Selector()
				.from(packagePath)
				.filterClasses(ClassConditions.subTypeOf(WebServices.class))
				.findClasses();

		//--Enregistrement des fichiers java annotés
		for (final Class<? extends WebServices> webServicesClass : webServicesClasses) {
			webServiceDefinitions.addAll(scanWebServices(webServicesClass));
		}
	}

	private static List<StudioWebServiceDefinition> scanWebServices(final Class<? extends WebServices> webServicesClass) {
		Assertion.check().notNull(webServicesClass);
		//-----
		return Arrays.stream(webServicesClass.getMethods())
				.map(VegaWebServicesResourceParserPlugin::buildWebServiceDefinition)
				.filter(webServiceDefinitionOptional -> webServiceDefinitionOptional.isPresent())
				.map(webServiceDefinitionOptional -> webServiceDefinitionOptional.get())
				.collect(Collectors.toList());
	}

	private static Optional<StudioWebServiceDefinition> buildWebServiceDefinition(final Method method) {
		final PathPrefix pathPrefixAnnotation = method.getDeclaringClass().getAnnotation(PathPrefix.class);
		final String pathPrefix = pathPrefixAnnotation != null ? pathPrefixAnnotation.value() : "";
		for (final Annotation annotation : method.getAnnotations()) {
			if (annotation instanceof GET) {
				return Optional.of(createStudioWebServiceDefinition(method, StudioWebServiceDefinition.Verb.Get, pathPrefix + ((GET) annotation).value()));
			} else if (annotation instanceof POST) {
				return Optional.of(createStudioWebServiceDefinition(method, StudioWebServiceDefinition.Verb.Post, pathPrefix + ((POST) annotation).value()));
			} else if (annotation instanceof PUT) {
				return Optional.of(createStudioWebServiceDefinition(method, StudioWebServiceDefinition.Verb.Put, pathPrefix + ((PUT) annotation).value()));
			} else if (annotation instanceof PATCH) {
				return Optional.of(createStudioWebServiceDefinition(method, StudioWebServiceDefinition.Verb.Patch, pathPrefix + ((PATCH) annotation).value()));
			} else if (annotation instanceof DELETE) {
				return Optional.of(createStudioWebServiceDefinition(method, StudioWebServiceDefinition.Verb.Delete, pathPrefix + ((DELETE) annotation).value()));
			}
		}
		return Optional.empty();
	}

	private static StudioWebServiceDefinition createStudioWebServiceDefinition(final Method method, final StudioWebServiceDefinition.Verb verb, final String path) {
		final Type returnType = method.getGenericReturnType();
		final boolean isVoid = void.class.isAssignableFrom(returnType.getClass());
		final Cardinality cardinality = getCardinalityFromType(returnType);

		final String doc = method.isAnnotationPresent(Doc.class) ? method.getAnnotation(Doc.class).value() : "";
		final Type[] paramType = method.getGenericParameterTypes();
		final Annotation[][] parameterAnnotation = method.getParameterAnnotations();

		final List<StudioWebServiceParam> webServiceParams = new ArrayList<>();

		for (int i = 0; i < paramType.length; i++) {
			final Optional<StudioWebServiceParam> webServiceParamOpt = buildWebServiceParam(parameterAnnotation[i], paramType[i]);
			webServiceParamOpt.ifPresent(webServiceParams::add);
		}

		//---
		final String[] fullPackageNameSplited = method.getDeclaringClass().getPackage().toString().split("\\.");
		final String moduleName = fullPackageNameSplited[fullPackageNameSplited.length - 1];
		//---
		return new StudioWebServiceDefinition(
				"StWs" + verb + normalizePath(path),
				verb,
				path,
				webServiceParams,
				isVoid ? Optional.empty() : Optional.of(new StudioWebServiceResponseContent(returnType, cardinality)),
				moduleName,
				Optional.of(method.getDeclaringClass().getSimpleName().replaceAll("WebServices", "")),
				method.getName(),
				doc);
	}

	private static Optional<StudioWebServiceParam> buildWebServiceParam(final Annotation[] annotations, final Type paramType) {
		//		 if (DtListState.class.equals(paramType)) {
		//			builder.with(WebServiceParamType.Query, "listState"); //DtListState don't need to be named, it will be retrieve from query
		//		}

		for (final Annotation annotation : annotations) {
			if (annotation instanceof PathParam) {
				return Optional.of(new StudioWebServiceParam(((PathParam) annotation).value(), WebServiceParamLocation.Path, paramType, getCardinalityFromType(paramType)));
			} else if (annotation instanceof QueryParam) {
				return Optional.of(new StudioWebServiceParam(((QueryParam) annotation).value(), WebServiceParamLocation.Query, paramType, getCardinalityFromType(paramType)));
			} else if (annotation instanceof HeaderParam) {
				return Optional.of(new StudioWebServiceParam(((HeaderParam) annotation).value(), WebServiceParamLocation.Header, paramType, getCardinalityFromType(paramType)));
			} else if (annotation instanceof InnerBodyParam) {
				return Optional.of(new StudioWebServiceParam(((InnerBodyParam) annotation).value(), WebServiceParamLocation.InnerBody, paramType, getCardinalityFromType(paramType)));
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
	public List<String> getHandledResourceTypes() {
		return Collections.singletonList("webservice");
	}

}
