/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2026, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.task.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.vertigo.core.lang.VUserException;
import io.vertigo.core.lang.WrappedException;
import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.datamodel.smarttype.definitions.ConstraintException;

/**
 * The generated task tests only check the syntax : an error related to the data does not fail them.
 */
class TaskTestDaoCheckerTest {
	private final TaskTestDaoChecker checker = new TaskTestDaoChecker();

	@Test
	void dataErrorsIgnored() {
		assertDoesNotThrow(() -> checker.semantics(() -> {
			throw new VUserException("user error");
		}));
		// a constraint of the data, as a mandatory result when the task returns no row (TaskResult)
		assertDoesNotThrow(() -> checker.semantics(() -> {
			throw WrappedException.wrap(new ConstraintException(LocaleMessageText.of("mandatory value")));
		}));
	}

	@Test
	void otherErrorsFail() {
		assertThrows(IllegalStateException.class, () -> checker.semantics(() -> {
			throw new IllegalStateException("syntax error");
		}));
		assertThrows(WrappedException.class, () -> checker.semantics(() -> {
			throw WrappedException.wrap(new java.sql.SQLException("syntax error"));
		}));
	}
}
