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

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.commons.peg.PegNoMatchFoundException;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslWordsRule;

public final class DslWordListRuleTest {
	private final DslWordsRule wordListRule = new DslWordsRule();

	@Test
	public void testList0() throws PegNoMatchFoundException {
		final List<String> list = wordListRule
				.parse("[ ]")
				.getValue();
		Assertions.assertEquals(0, list.size());
	}

	@Test
	public void testList1() throws PegNoMatchFoundException {
		final List<String> list = wordListRule
				.parse("[BLEU, VerT, ROUGE ]")
				.getValue();
		Assertions.assertEquals(3, list.size());
		Assertions.assertTrue(list.contains("BLEU"));
		Assertions.assertTrue(list.contains("VerT"));
		Assertions.assertTrue(list.contains("ROUGE"));
	}

	@Test
	public void testList2() throws PegNoMatchFoundException {
		final List<String> list = wordListRule
				.parse("[BLEU, VERT, ROUGE ]")
				.getValue();
		Assertions.assertEquals(3, list.size());
		Assertions.assertTrue(list.contains("BLEU"));
		Assertions.assertTrue(list.contains("VERT"));
		Assertions.assertTrue(list.contains("ROUGE"));
	}

	@Test
	public void testList3() {
		Assertions.assertThrows(Exception.class, () -> {
			final List<String> list = wordListRule
					.parse(" [BLEU  ,	VERT,   ROUGE ,  Orange,] ")
					.getValue();
			Assertions.fail("liste :" + list);
		});
	}

	@Test
	public void testList4() {
		Assertions.assertThrows(Exception.class, () -> {
			final List<String> list = wordListRule
					.parse(" [ , BLEU,VERT,   ROUGE ,  Violet] ")
					.getValue();
			Assertions.fail("liste :" + list);
		});
	}

	@Test
	public void testList5() throws PegNoMatchFoundException {
		final List<String> list = wordListRule
				.parse("[BLEU ]")
				.getValue();
		Assertions.assertEquals(1, list.size());
		Assertions.assertTrue(list.contains("BLEU"));
	}
}
