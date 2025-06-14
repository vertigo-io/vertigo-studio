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
package io.vertigo.studio.source.vertigo.dsl;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.commons.peg.PegNoMatchFoundException;
import io.vertigo.commons.peg.PegResult;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.raw.DslPropertyEntry;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslPropertyDeclarationRule;

public final class DslPropertyDeclarationRuleTest {
	private static final String LABEL = "LABEL";
	private static final String SIZE = "SIZE";

	private static final DslPropertyDeclarationRule MAIN;
	static {
		final Set<String> propertyNames = new HashSet<>();
		propertyNames.add(LABEL);
		propertyNames.add(SIZE);
		MAIN = new DslPropertyDeclarationRule(propertyNames);
	}

	@Test
	public void test() throws PegNoMatchFoundException {
		final String text = "label   : \"BLeU\", non reconnu";
		final PegResult<DslPropertyEntry> cursor = MAIN
				.parse(text);
		final DslPropertyEntry propertyEntry = cursor.getValue();
		Assertions.assertEquals(LABEL, propertyEntry.getPropertyName());
		Assertions.assertEquals("BLeU", propertyEntry.getPropertyValueAsString());
		Assertions.assertEquals(text.length() - " non reconnu".length(), cursor.getIndex()); //On vérfifie que le pointeur a avancé jusqu'à 'non reconnu'

	}

	@Test
	public void test2() throws PegNoMatchFoundException {
		final String text = "label  :    \" vert \"";
		final PegResult<DslPropertyEntry> cursor = MAIN
				.parse(text);
		//On ne met pas de séparateur final et on met un espace
		final DslPropertyEntry propertyEntry = cursor.getValue();
		Assertions.assertEquals(LABEL, propertyEntry.getPropertyName());
		Assertions.assertEquals(" vert ", propertyEntry.getPropertyValueAsString()); //l'espace doit être conservé
		Assertions.assertEquals(text.length(), cursor.getIndex());
	}

	@Test
	public void test3() throws PegNoMatchFoundException {
		final String text = "size   : \"54\",";
		final PegResult<DslPropertyEntry> cursor = MAIN
				.parse(text);

		final DslPropertyEntry propertyEntry = cursor.getValue();
		Assertions.assertEquals(SIZE, propertyEntry.getPropertyName());
		Assertions.assertEquals("54", propertyEntry.getPropertyValueAsString());
		Assertions.assertEquals(text.length(), cursor.getIndex());
	}

	@Test
	public void testFail() {
		Assertions.assertThrows(PegNoMatchFoundException.class, () -> {
			final String text = "maxlength   : \"54\";";
			//La propriété maxlength n'est pas enregistrée
			MAIN.parse(text);
		});
	}

	@Test
	public void testFail2() {
		Assertions.assertThrows(PegNoMatchFoundException.class, () -> {
			final String text = "label  :    vert \"";
			MAIN.parse(text); //On omet la quote de début
		});
	}

}
