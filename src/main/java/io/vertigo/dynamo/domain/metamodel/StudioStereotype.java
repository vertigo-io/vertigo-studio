package io.vertigo.dynamo.domain.metamodel;

/**
 * The type of dt (Will be refactored in next releases, not homogeneous)
 *
 */
public enum StudioStereotype {
	MasterData, //
	StaticMasterData, //
	KeyConcept, //
	ValueObject, //By default
	Entity, //
	Fragment;

	/**
	 * Returns true if the type of dt is persistent (Will be refactored in next releases)
	 * @return if the stereotype is persistent
	 */
	public boolean isPersistent() {
		return this == Entity
				|| this == KeyConcept
				|| this == MasterData
				|| this == StaticMasterData;
	}
}
