package io.vertigo.shiny.models.media.geomap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyGeoMapBuilder implements Builder<ShinyGeoMap> {
	private UUID _id;
	private String _title;
	private final List<ShinyGeoPoint> _geoPoints = new ArrayList<>();

	public ShinyGeoMapBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

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
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyGeoMap(_id, _title, _geoPoints);
	}
}
