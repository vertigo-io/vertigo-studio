/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2021, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.notebook;

import io.vertigo.core.lang.Assertion;

public abstract class AbstractSketch implements Sketch {
	private final String prefix;
	private final SketchKey key;

	protected AbstractSketch(final String name) {
		final SkecthPrefix sketchPrefix = this.getClass().getAnnotation(SkecthPrefix.class);
		Assertion.check().isNotNull(sketchPrefix, "Annotation '@SkecthPrefix' not found on {0}", this.getClass().getName());
		prefix = sketchPrefix.value();
		//---
		Assertion.check()
				.isNotNull(prefix, "Annotation '@SkecthPrefix' not found on {0}", this.getClass().getName())
				.isNotBlank(prefix)
				.isNotBlank(name)
				.isTrue(name.startsWith(prefix), "The sketch name {0} must start with {1}", name, prefix)
				.isTrue(Character.isUpperCase(name.charAt(prefix.length())), "the name of the sketch {0} must be in UpperCamelCase", name)
				.isTrue(Sketch.REGEX_SKETCH_NAME.matcher(name).matches(), "name's sketch {0} must match the pattern {1}", name, Sketch.REGEX_SKETCH_NAME);
		//---
		key = SketchKey.of(name);
	}

	/** {@inheritDoc} */
	@Override
	public final SketchKey getKey() {
		return key;
	}

	/** {@inheritDoc} */
	@Override
	public final String getLocalName() {
		return getKey().getName().substring(prefix.length());
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return key.toString();
	}

}
