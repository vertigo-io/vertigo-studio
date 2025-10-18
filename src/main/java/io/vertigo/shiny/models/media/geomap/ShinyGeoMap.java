package io.vertigo.shiny.models.media.geomap;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyGeoMap(
		String title,
		List<ShinyGeoPoint> geoPoints) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyGeoMap";
	}


	public ShinyGeoMap {
		Assertion.check()
				.isNotBlank(title, "Title cannot be blank");
		Assertion.check()
				.isNotNull(geoPoints, "GeoPoints list cannot be null");
	}
}