package io.vertigo.shiny.models.dataviz.geomap;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import jakarta.annotation.Nonnull;

public final class ShinyGeoMapBuilder implements Builder<ShinyGeoMap> {

	private String _title;
	private final List<ShinyGeoPoint> _geoPoints = new ArrayList<>();

	public ShinyGeoMapBuilder withTitle(@Nonnull final String title) {
		Assertion.check().isNotBlank(title);
		this._title = title;
		return this;
	}

	public ShinyGeoMapBuilder addAllGeoPoints(@Nonnull final List<ShinyGeoPoint> geoPoints) {
		Assertion.check().isNotNull(geoPoints);
		//---
		this._geoPoints.addAll(geoPoints);
		return this;
	}

	public ShinyGeoMapBuilder addGeoPoint(@Nonnull final ShinyGeoPoint geoPoint) {
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
