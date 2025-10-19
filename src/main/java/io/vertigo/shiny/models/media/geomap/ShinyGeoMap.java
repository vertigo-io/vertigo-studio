package io.vertigo.shiny.models.media.geomap;

import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyGeoMap(
		UUID id,
		String title,
		List<ShinyGeoPoint> geoPoints) implements ShinyModel {

	public ShinyGeoMap {
		Assertion.check()
				.isNotBlank(title, "Title cannot be blank")
				.isNotNull(geoPoints, "GeoPoints list cannot be null");
	}
}
