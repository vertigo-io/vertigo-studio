package io.vertigo.vortex.gold.module;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.VXUID;

/**
 * Represents a business entity.
 * An entity has a name and a list of attributes.
 * For example, a 'Book' entity could have attributes like 'title' and 'author'.
 * @synthetic
 */
public record VXEntity(
		VXUID uid, //entity uid aka class name/ table name
		String description,
		VXId id, //id or pk 
		List<VXAttribute> attributes,
		List<VXLink> links) {

	public VXEntity {
		Assertion.check()
				.isNotNull(uid)
				.isNotNull(id)
				.isNotNull(attributes)
				.isNotNull(links);
	}
}
