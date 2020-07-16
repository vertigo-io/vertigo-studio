package io.vertigo.studio.tools;

import io.vertigo.core.lang.Assertion;

/**
 * This class provides usefull utilies about sketchs.
 *
 * @author  pchretien
 */
public final class SketchUtil {
	private SketchUtil() {
		super();
	}

	/**
	 * Returns the short name of the sketch.
	 * @param sketchName Name of the sketch
	 * @param definitionClass Type of the sketch
	 * @return the short name of the sketch
	 */
	public static String getLocalName(final String sketchName, final String prefix) {
		Assertion.check()
				.isNotBlank(sketchName)
				.isNotBlank(prefix);
		//-----
		//On enléve le prefix et le separateur.
		//On vérifie aussi que le prefix est OK
		Assertion.check().isTrue(sketchName.startsWith(prefix), "Le nom de la définition '{0}' ne commence pas par le prefix attendu : '{1}'", sketchName, prefix);
		return sketchName.substring(prefix.length());
	}
}
