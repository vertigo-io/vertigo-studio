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
package io.vertigo.studio.impl.source.dsl.raw;

import io.vertigo.core.lang.Assertion;

public final class DslRawKey {
	private final String name;

	public static DslRawKey of(final String name) {
		return new DslRawKey(name);
	}

	private DslRawKey(final String name) {
		Assertion.check().isNotBlank(name);
		//---
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return (obj instanceof DslRawKey) ? name.equals(((DslRawKey) obj).name) : false;
	}

	@Override
	public String toString() {
		return name;
	}
}
