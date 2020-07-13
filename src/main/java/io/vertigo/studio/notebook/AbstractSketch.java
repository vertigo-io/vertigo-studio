package io.vertigo.studio.notebook;

import io.vertigo.core.lang.Assertion;

public abstract class AbstractSketch implements Sketch {
	private final String prefix;
	private final String name;

	protected AbstractSketch(final String name) {
		final SkecthPrefix sketchPrefix = this.getClass().getAnnotation(SkecthPrefix.class);
		prefix = sketchPrefix == null ? null : sketchPrefix.value();
		//---
		Assertion.check()
				.isNotNull(prefix, "Annotation '@SkecthPrefix' not found on {0}", this.getClass().getName())
				.isNotBlank(prefix)
				.isNotBlank(name)
				.isTrue(name.startsWith(prefix), "The sketch name {0} must start with {1}", name, prefix)
				.isTrue(name.length() > prefix.length(), "A sketch must have a name")
				.isTrue(Character.isUpperCase(name.charAt(prefix.length())), "the name of the sketch {0} must be in UpperCamelCase", name)
				.isTrue(Sketch.REGEX_SKETCH_NAME.matcher(name).matches(), "name's sketch {0} must match the pattern {1}", name, Sketch.REGEX_SKETCH_NAME);
		//---
		this.name = name;
	}

	/** {@inheritDoc} */
	@Override
	public final String getName() {
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public final String getLocalName() {
		return getName().substring(prefix.length());
	}

	/** {@inheritDoc} */
	@Override
	public final String toString() {
		return name;
	}

}
