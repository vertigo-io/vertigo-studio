package io.vertigo.shiny.models.media.geomap;

public record ShinyGeoPoint(
		double latitude,
		double longitude,
		String label) {

	public static ShinyGeoPoint of(final double latitude, final double longitude) {
		return new ShinyGeoPoint(latitude, longitude, null);
	}

	public static ShinyGeoPoint of(final double latitude, final double longitude, final String label) {
		return new ShinyGeoPoint(latitude, longitude, label);
	}

}
