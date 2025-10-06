package io.vertigo.shiny.components.media.geomap;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyGeoMap(
		String title,
		List<ShinyGeoPoint> geoPoints) implements ShinyComponent {

	@Override
	public String type() {
		return "geoMap";
	}


	public ShinyGeoMap {
		Assertion.check()
				.isNotBlank(title, "Title cannot be blank");
		Assertion.check()
				.isNotNull(geoPoints, "GeoPoints list cannot be null");
	}
}