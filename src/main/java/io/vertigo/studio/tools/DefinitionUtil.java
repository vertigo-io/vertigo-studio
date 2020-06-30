package io.vertigo.studio.tools;

import io.vertigo.core.lang.Assertion;

/**
 * This class provides usefull Utilitaire concernant les Definitions.
 *
 * @author  pchretien
 */
public final class DefinitionUtil {
	private DefinitionUtil() {
		super();
	}

	/**
	 * Returns the short name of the definition.
	 * @param definitionName Name of the definition
	 * @param definitionClass Type of the definition
	 * @return the short name of the definition
	 */
	public static String getLocalName(final String definitionName, final String prefix) {
		Assertion.check()
				.isNotBlank(definitionName)
				.isNotBlank(prefix);
		//-----
		//On enléve le prefix et le separateur.
		//On vérifie aussi que le prefix est OK
		Assertion.check().isTrue(definitionName.startsWith(prefix), "Le nom de la définition '{0}' ne commence pas par le prefix attendu : '{1}'", definitionName, prefix);
		return definitionName.substring(prefix.length());
	}
}
