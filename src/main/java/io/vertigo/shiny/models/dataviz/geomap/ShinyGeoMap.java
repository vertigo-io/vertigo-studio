package io.vertigo.shiny.models.dataviz.geomap;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyGeoMap(
		String title,
		List<ShinyGeoPoint> geoPoints) implements ShinyModel {

	public ShinyGeoMap {
		Assertion.check()
				.isNotBlank(title, "Title cannot be blank")
				.isNotNull(geoPoints, "GeoPoints list cannot be null");
	}
}
