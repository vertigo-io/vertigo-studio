package io.vertigo.studio.notebook;

import java.util.regex.Pattern;

public interface Sketch {

	/**
	 * A skecth must have a unique name, which matches the following patterns :
	 * PrefixAaaaBbbb123
	 * or
	 * PrefixAaaaBbbb123$abcAbc123
	 */
	Pattern REGEX_SKETCH_NAME = Pattern.compile("[A-Z][a-zA-Z0-9]{2,60}([$][a-z][a-zA-Z0-9]{2,60})?");

	/**
	 * @return The key of the sketch
	 */
	SketchKey getKey();

	/**
	 * @return The short name of the sketch without prefix
	 */
	String getLocalName();
}
