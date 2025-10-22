package io.vertigo.shiny.models.dataviz.geomap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyGeoMapBuilder implements Builder<ShinyGeoMap> {

	private String _title;
	private final List<ShinyGeoPoint> _geoPoints = new ArrayList<>();



	public ShinyGeoMapBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		this._title = title;
		return this;
	}

	public ShinyGeoMapBuilder addAllGeoPoints(final List<ShinyGeoPoint> geoPoints) {
		Assertion.check().isNotNull(geoPoints);
		//---
		this._geoPoints.addAll(geoPoints);
		return this;
	}

	public ShinyGeoMapBuilder addGeoPoint(final ShinyGeoPoint geoPoint) {
		Assertion.check().isNotNull(geoPoint);
		//---
		this._geoPoints.add(geoPoint);
		return this;
	}

	@Override
	public ShinyGeoMap build() {

		return new ShinyGeoMap(null, _title, _geoPoints);
	}
}
