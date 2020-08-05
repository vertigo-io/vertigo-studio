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
package io.vertigo.studio.metamodel.vertigo.dsl;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.commons.peg.PegNoMatchFoundException;
import io.vertigo.commons.peg.PegResult;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.definition.DslSketchEntry;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslSketchEntryRule;

public final class DslDefinitionEntryRuleTest {
	private static final DslSketchEntryRule MAIN = new DslSketchEntryRule(Arrays.asList("myFirstProperty", "myLastProperty"));

	@Test
	public void test0() throws PegNoMatchFoundException {
		final String text = "myFirstProperty : [BLEU ], non reconnu";
		final PegResult<DslSketchEntry> cursor = MAIN
				.parse(text);
		final DslSketchEntry dslSketchEntry = cursor.getValue();
		Assertions.assertEquals("myFirstProperty", dslSketchEntry.getFieldName());
		Assertions.assertEquals(1, dslSketchEntry.getDefinitionNames().size());
		Assertions.assertTrue(dslSketchEntry.getDefinitionNames().contains("BLEU"));
		Assertions.assertEquals(text.length() - " non reconnu".length(), cursor.getIndex());
	}

	@Test
	public void test1() throws PegNoMatchFoundException {
		final String text = "myFirstProperty : [BLEU, VerT, ROUGE, T_REX ], non reconnu";
		final PegResult<DslSketchEntry> cursor = MAIN
				.parse(text);
		final DslSketchEntry dslSketchEntry = cursor.getValue();
		Assertions.assertEquals("myFirstProperty", dslSketchEntry.getFieldName());
		Assertions.assertEquals(4, dslSketchEntry.getDefinitionNames().size());
		Assertions.assertTrue(dslSketchEntry.getDefinitionNames().contains("VerT"));
		Assertions.assertEquals(text.length() - " non reconnu".length(), cursor.getIndex());

	}

	@Test
	public void test2() throws PegNoMatchFoundException {
		final String text = "myLastProperty : [ ],";
		final PegResult<DslSketchEntry> cursor = MAIN
				.parse(text);

		final DslSketchEntry dslSketchEntry = cursor.getValue();
		Assertions.assertEquals("myLastProperty", dslSketchEntry.getFieldName());
		Assertions.assertEquals(0, dslSketchEntry.getDefinitionNames().size());
		Assertions.assertEquals(text.length(), cursor.getIndex());
	}

	@Test
	public void test3() throws PegNoMatchFoundException {
		final String text = "myFirstProperty    :    [BLEU,VerT,    ROUGE    ]";
		final PegResult<DslSketchEntry> cursor = MAIN
				.parse(text);
		final DslSketchEntry xDefinitionEntry = cursor.getValue();
		Assertions.assertEquals("myFirstProperty", xDefinitionEntry.getFieldName());
		Assertions.assertEquals(3, xDefinitionEntry.getDefinitionNames().size());
		Assertions.assertTrue(xDefinitionEntry.getDefinitionNames().contains("VerT"));
		Assertions.assertEquals(text.length(), cursor.getIndex());
	}

	@Test
	public void test4() throws PegNoMatchFoundException {
		final String text = "myFirstProperty : BLEU,";
		final PegResult<DslSketchEntry> cursor = MAIN
				.parse(text);
		final DslSketchEntry xDefinitionEntry = cursor.getValue();
		Assertions.assertEquals("myFirstProperty", xDefinitionEntry.getFieldName());
		Assertions.assertEquals(1, xDefinitionEntry.getDefinitionNames().size());
		Assertions.assertTrue(xDefinitionEntry.getDefinitionNames().contains("BLEU"));
		Assertions.assertEquals(text.length(), cursor.getIndex());
	}

	@Test
	public void testFail1() {
		Assertions.assertThrows(PegNoMatchFoundException.class, () -> {
			final String text = "myLastProperty : [BLEU;";
			//on ne ferme pas l'accolade
			final PegResult<DslSketchEntry> cursor = MAIN
					.parse(text); //<-- an exception is expected here
			Assertions.assertNotNull(cursor);
		});
	}

	@Test
	public void testFail2() {
		Assertions.assertThrows(PegNoMatchFoundException.class, () -> {
			final String text = "myUnknownProperty : BLEU";
			//on positionne un nom erroné de propriété
			MAIN.parse(text);
		});
	}
}
