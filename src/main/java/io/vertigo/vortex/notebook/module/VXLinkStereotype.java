package io.vertigo.vortex.notebook.module;

public enum VXLinkStereotype {
	/**
	 * A mandatory link where many source entities can refer to ONE target entity.
	 */
	MANY_TO_ONE,
	/**
	 * An optional link where many source entities can refer to one target entity.
	 */
	MANY_TO_OPTIONAL,
	/**
	 * A mandatory link where one source entity refers to ONE target entity.
	 */
	ONE_TO_ONE,
	/**
	 * An optional link where one source entity refers to one target entity.
	 */
	ONE_TO_OPTIONAL,
	/**
	 * A link where many source entities can refer to many target entities.
	 */
	MANY_TO_MANY;
}
