package io.vertigo.vortex.notebook.module;

public enum VXLinkStereotype {
	/**
	 * A link where many source entities can refer to one target entity.
	 */
	MANY_TO_ONE,
	/**
	 * A link where one source entity refers to one target entity.
	 */
	ONE_TO_ONE,
	/**
	 * A link where many source entities can refer to many target entities.
	 */
	MANY_TO_MANY;
}
