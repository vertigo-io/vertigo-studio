package io.vertigo.vortex.notebook.module;

public enum VXLinkStereotype {
	/**
	 * A strong "part-of" relationship where the part cannot exist without the whole. (Composition)
	 */
	PART_OF,
	/**
	 * A standard relationship between two peer entities. (Association)
	 * or a simple Aggregation between a member and a group
	 */
	LINK;
}
