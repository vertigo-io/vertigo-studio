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
package io.vertigo.studio.metamodel.vertigo.eaxmi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.core.AbstractTestCaseJU5;
import io.vertigo.core.lang.BasicType;
import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.datamodel.impl.smarttype.constraint.ConstraintRegex;
import io.vertigo.datamodel.impl.smarttype.formatter.FormatterDefault;
import io.vertigo.datamodel.impl.smarttype.formatter.FormatterNumber;
import io.vertigo.studio.metamodel.domain.ConstraintDefinition;
import io.vertigo.studio.metamodel.domain.Domain;
import io.vertigo.studio.metamodel.domain.FormatterDefinition;
import io.vertigo.studio.metamodel.domain.StudioDtDefinition;
import io.vertigo.studio.plugins.metamodel.vertigo.StudioDefinitionProvider;

/**
 * Test de l'implémentation standard.
 *
 * @author pchretien
 */
public final class EAXmiEnvironmentManagerTest extends AbstractTestCaseJU5 {
	private DefinitionSpace definitionSpace;

	@Override
	protected NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.beginBoot()
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.endBoot()
				.addModule(ModuleConfig.builder("myApp")
						.addDefinitionProvider(DefinitionProviderConfig.builder(StudioDefinitionProvider.class)
								.addDefinitionResource("xmi", "io/vertigo/studio/metamodel/vertigo/eaxmi/data/demo.xml")
								.addDefinitionResource("kpr", "io/vertigo/studio/metamodel/vertigo/eaxmi/data/domain.kpr")
								.build())
						.build())
				.build();
	}

	@Override
	protected void doSetUp() throws Exception {
		definitionSpace = getApp().getDefinitionSpace();
	}

	@Test
	public void testConstraint() {
		final ConstraintDefinition constraint = definitionSpace.resolve("CkTelephone", ConstraintDefinition.class);
		Assertions.assertEquals(ConstraintRegex.class.getName(), constraint.getConstraintClassName());
	}

	@Test
	public void testDefaultFormatter() {
		final FormatterDefinition formatter = definitionSpace.resolve("FmtDefault", FormatterDefinition.class);
		Assertions.assertEquals(FormatterDefault.class.getName(), formatter.getFormatterClassName());
	}

	@Test
	public void testFormatter() {
		final FormatterDefinition formatter = definitionSpace.resolve("FmtTaux", FormatterDefinition.class);
		Assertions.assertEquals(FormatterNumber.class.getName(), formatter.getFormatterClassName());
	}

	@Test
	public void testDomain() {
		final io.vertigo.studio.metamodel.domain.Domain domain = definitionSpace.resolve("DoEmail", Domain.class);
		Assertions.assertEquals(BasicType.String, domain.getDataType());
		Assertions.assertEquals(FormatterDefault.class.getName(), domain.getFormatterDefinition().getFormatterClassName());
	}

	@Test
	public void testDtDefinition() {
		final StudioDtDefinition dtDefinition = definitionSpace.resolve("StDtFamille", StudioDtDefinition.class);
		Assertions.assertEquals("io.vertigo.dynamock.domain.famille.Famille", dtDefinition.getClassCanonicalName());
		Assertions.assertTrue(dtDefinition.isPersistent());
		Assertions.assertEquals("io.vertigo.dynamock.domain.famille", dtDefinition.getPackageName());
	}
}
