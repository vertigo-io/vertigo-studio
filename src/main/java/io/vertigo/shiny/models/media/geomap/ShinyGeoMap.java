package io.vertigo.shiny.models.media.geomap;

import java.util.List;
import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyGeoMap(
		UUID id,
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