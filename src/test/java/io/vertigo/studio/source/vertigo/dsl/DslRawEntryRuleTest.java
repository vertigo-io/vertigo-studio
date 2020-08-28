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
package io.vertigo.studio.source.vertigo.dsl;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.commons.peg.PegNoMatchFoundException;
import io.vertigo.commons.peg.PegResult;
import io.vertigo.studio.impl.source.dsl.raw.DslRawKey;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.raw.DslRawEntry;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslRawEntryRule;

public final class DslRawEntryRuleTest {
	private static final DslRawEntryRule MAIN = new DslRawEntryRule(Arrays.asList("myFirstProperty", "myLastProperty"));

	@Test
	public void test0() throws PegNoMatchFoundException {
		final String text = "myFirstProperty : [BLEU ], non reconnu";
		final PegResult<DslRawEntry> cursor = MAIN
				.parse(text);
		final DslRawEntry dslSketchEntry = cursor.getValue();
		Assertions.assertEquals("myFirstProperty", dslSketchEntry.getFieldName());
		Assertions.assertEquals(1, dslSketchEntry.getRawKeys().size());
		Assertions.assertTrue(dslSketchEntry.getRawKeys().contains(DslRawKey.of("BLEU")));
		Assertions.assertEquals(text.length() - " non reconnu".length(), cursor.getIndex());
	}

	@Test
	public void test1() throws PegNoMatchFoundException {
		final String text = "myFirstProperty : [BLEU, VerT, ROUGE, T_REX ], non reconnu";
		final PegResult<DslRawEntry> cursor = MAIN
				.parse(text);
		final DslRawEntry dslSketchEntry = cursor.getValue();
		Assertions.assertEquals("myFirstProperty", dslSketchEntry.getFieldName());
		Assertions.assertEquals(4, dslSketchEntry.getRawKeys().size());
		Assertions.assertTrue(dslSketchEntry.getRawKeys().contains(DslRawKey.of("VerT")));
		Assertions.assertEquals(text.length() - " non reconnu".length(), cursor.getIndex());

	}

	@Test
	public void test2() throws PegNoMatchFoundException {
		final String text = "myLastProperty : [ ],";
		final PegResult<DslRawEntry> cursor = MAIN
				.parse(text);

		final DslRawEntry dslSketchEntry = cursor.getValue();
		Assertions.assertEquals("myLastProperty", dslSketchEntry.getFieldName());
		Assertions.assertEquals(0, dslSketchEntry.getRawKeys().size());
		Assertions.assertEquals(text.length(), cursor.getIndex());
	}

	@Test
	public void test3() throws PegNoMatchFoundException {
		final String text = "myFirstProperty    :    [BLEU,VerT,    ROUGE    ]";
		final PegResult<DslRawEntry> cursor = MAIN
				.parse(text);
		final DslRawEntry xDefinitionEntry = cursor.getValue();
		Assertions.assertEquals("myFirstProperty", xDefinitionEntry.getFieldName());
		Assertions.assertEquals(3, xDefinitionEntry.getRawKeys().size());
		Assertions.assertTrue(xDefinitionEntry.getRawKeys().contains(DslRawKey.of("VerT")));
		Assertions.assertEquals(text.length(), cursor.getIndex());
	}

	@Test
	public void test4() throws PegNoMatchFoundException {
		final String text = "myFirstProperty : BLEU,";
		final PegResult<DslRawEntry> cursor = MAIN
				.parse(text);
		final DslRawEntry xDefinitionEntry = cursor.getValue();
		Assertions.assertEquals("myFirstProperty", xDefinitionEntry.getFieldName());
		Assertions.assertEquals(1, xDefinitionEntry.getRawKeys().size());
		Assertions.assertTrue(xDefinitionEntry.getRawKeys().contains(DslRawKey.of("BLEU")));
		Assertions.assertEquals(text.length(), cursor.getIndex());
	}

	@Test
	public void testFail1() {
		Assertions.assertThrows(PegNoMatchFoundException.class, () -> {
			final String text = "myLastProperty : [BLEU;";
			//on ne ferme pas l'accolade
			final PegResult<DslRawEntry> cursor = MAIN
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
