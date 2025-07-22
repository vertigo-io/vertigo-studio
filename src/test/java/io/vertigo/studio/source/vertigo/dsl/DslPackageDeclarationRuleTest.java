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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.commons.peg.PegNoMatchFoundException;
import io.vertigo.commons.peg.PegResult;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslPackageDeclarationRule;

public final class DslPackageDeclarationRuleTest {
	private static final DslPackageDeclarationRule PACKAGE_DECLARATION_RULE = new DslPackageDeclarationRule();

	@Test
	public void testExpression() throws PegNoMatchFoundException {
		final PegResult<String> result = PACKAGE_DECLARATION_RULE
				.parse("package io.vertigo  xxxx");
		Assertions.assertEquals("io.vertigo", result.getValue());
		Assertions.assertEquals("package io.vertigo".length(), result.getIndex());
	}

	@Test
	public void testMalFormedExpression() {
		Assertions.assertThrows(Exception.class, () -> {
			final PegResult<String> result = PACKAGE_DECLARATION_RULE
					.parse("packageio.vertigo");//<-- en exception is excpected here
			Assertions.assertNotNull(result);
		});
	}

	@Test
	public void testMalFormedExpression2() {
		Assertions.assertThrows(Exception.class, () -> {
			final PegResult<String> cursor = PACKAGE_DECLARATION_RULE
					.parse("  packageio.vertigo");//<-- en exception is excpected here
			Assertions.assertNotNull(cursor);
		});
	}
}
