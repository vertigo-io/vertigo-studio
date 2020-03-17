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
package io.vertigo.studio.plugins.mda.domain.java.model;

import java.util.List;

import freemarker.ext.beans.StringModel;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import io.vertigo.core.lang.Assertion;
import io.vertigo.dynamo.domain.metamodel.StudioDtDefinition;
import io.vertigo.dynamo.domain.metamodel.association.StudioAssociationNNDefinition;
import io.vertigo.dynamo.domain.metamodel.association.StudioAssociationSimpleDefinition;

/**
 * Méthode Freemarker 'annotations'.
 * si config.setSharedVariable("annotations", new TemplateMethodAnnotations(parameters));
 * Pour DtDefinition, le second parametre permet de déterminer isAbstract (default : false)
 * Exemple : annotations(dtDefinition)
 * Exemple : annotations(dtDefinition, true)
 * Exemple : annotations(dtField)
 * Exemple : annotations(associationNode)
 * TemplateMethodModelEx : les params sont considérés comme des Objets.
 *
 * @author  dchallas
 */
public final class MethodAnnotationsModel implements TemplateMethodModelEx {
	private final AnnotationWriter annotationWriter;

	/**
	 * Constructeur.
	 */
	public MethodAnnotationsModel() {
		annotationWriter = new AnnotationWriter();
	}

	/** {@inheritDoc}*/
	@Override
	public TemplateModel exec(final List params) throws TemplateModelException {
		Assertion.checkArgument(!params.isEmpty(), "Un parametre de type [DtField, DtDefinition, AssociationSimpleDefinition, AssociationNNDefinition] est obligatoire");
		//-----
		final Object param = params.get(0);
		final Object type;
		if (param instanceof SimpleScalar) {
			type = ((SimpleScalar) param).getAsString();
		} else {
			type = ((StringModel) param).getWrappedObject();
		}

		if (type instanceof StudioDtDefinition) {
			return new AnnotationsModel(annotationWriter, (StudioDtDefinition) type);
		} else if (type instanceof StudioDtFieldModel) {
			return new AnnotationsModel(annotationWriter, (StudioDtFieldModel) type);
		} else if (type instanceof StudioAssociationSimpleDefinition) {
			return new AnnotationsModel(annotationWriter, (StudioAssociationSimpleDefinition) type);
		} else if (type instanceof StudioAssociationNNDefinition) {
			return new AnnotationsModel(annotationWriter, (StudioAssociationNNDefinition) type);
		} else if (type instanceof String) {
			return new AnnotationsModel(annotationWriter, (String) type);
		} else {
			throw new TemplateModelException("Le type '" + type.getClass() + "' n''est pas dans la liste [DtField, DtDefinition, AssociationSimpleDefinition, AssociationNNDefinition]");
		}
	}

}
