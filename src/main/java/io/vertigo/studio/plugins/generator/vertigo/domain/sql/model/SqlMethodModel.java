/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2023, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.domain.sql.model;

import java.util.List;

import freemarker.ext.beans.StringModel;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DomainSketch;

/**
 * Méthode Freemarker 'sql'.
 * si config.setSharedVariable("sql", new TemplateMethodSql());
 * Exemple : ${sql(field.domain.dataType)}
 *
 * @author  dchallas
 */
public final class SqlMethodModel implements TemplateMethodModelEx {

	/** {@inheritDoc}*/
	@Override
	public TemplateModel exec(final List params) throws TemplateModelException {
		final Object type = ((StringModel) params.get(0)).getWrappedObject();
		if (type instanceof SqlStudioDtFieldModel) {
			final DomainSketch smartType = ((SqlStudioDtFieldModel) type).getSource().getDomain();
			return new SimpleScalar(getSqlType((smartType)));
		} else if (type instanceof DomainSketch) {
			return new SimpleScalar(getSqlType(((DomainSketch) type)));
		}
		throw new TemplateModelException("Le paramètre type n'est pas un Domain.");
	}

	private static String getSqlType(final DomainSketch domainSketch) {
		final String storeType = domainSketch.getProperties().getProperty("STORE_TYPE");
		Assertion.check().isNotNull(storeType, "La propriété StoreType est obligatoire dans le cas de génération de Sql. Domaine incriminé : {0}", domainSketch);
		return storeType;
	}

}
