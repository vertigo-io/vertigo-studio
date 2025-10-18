package io.vertigo.shiny.models.media.geomap;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyGeoMapBuilder implements Builder<ShinyGeoMap> {
	private String _title;
	private final List<ShinyGeoPoint> _geoPoints = new ArrayList<>();

	public ShinyGeoMapBuilder withTitle(final String title) {
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
		return new ShinyGeoMap(_title, _geoPoints);
	}
}
