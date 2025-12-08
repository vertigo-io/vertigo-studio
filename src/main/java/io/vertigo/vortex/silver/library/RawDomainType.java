
package io.vertigo.vortex.silver.library;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.vertigo.vortex.silver.RawElement;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RawDomainType(
		String name,
		String description,
		String dataType) implements RawElement {
}
