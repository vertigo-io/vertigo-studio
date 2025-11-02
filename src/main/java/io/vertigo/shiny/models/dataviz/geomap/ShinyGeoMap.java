package io.vertigo.shiny.models.dataviz.geomap;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyGeoMap(
		String title,
		List<ShinyGeoPoint> geoPoints) implements ShinyBlock {

	public ShinyGeoMap {
		Assertion.check()
				.isNotBlank(title, "Title cannot be blank")
				.isNotNull(geoPoints, "GeoPoints list cannot be null");
	}
}
