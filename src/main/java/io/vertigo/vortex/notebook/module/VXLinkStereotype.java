package io.vertigo.vortex.notebook.module;

public enum VXLinkStereotype {
	/**
	 * A strong "part-of" relationship where the part cannot exist without the whole. (Composition)
	 */
	PART_OF,
	/**
	 * A weak "has-a" relationship where the part can exist independently. (Aggregation)
	 */
	MEMBER_OF,
	/**
	 * A standard relationship between two peer entities. (Association)
	 */
	USES;
}
