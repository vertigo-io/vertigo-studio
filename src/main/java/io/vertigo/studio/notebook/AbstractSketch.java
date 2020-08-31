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
