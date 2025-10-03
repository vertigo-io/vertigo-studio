package io.vertigo.shiny.components.media.geomap;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyType;
import io.vertigo.shiny.components.ShinyComponent;

@ShinyType("geoMap")
public record ShinyGeoMap(
		String title,
		List<ShinyGeoPoint> geoPoints) implements ShinyComponent {

	public ShinyGeoMap {
		Assertion.check()
				.isNotBlank(title, "Title cannot be blank");
		Assertion.check()
				.isNotNull(geoPoints, "GeoPoints list cannot be null");
	}
}