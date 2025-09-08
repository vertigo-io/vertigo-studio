package io.vertigo.shell.systems.photo;

import java.util.ArrayList;
import java.util.List;

public final class PhotoContext {
	private static final List<PhotoInfo> photos = new ArrayList<>();

	public static List<PhotoInfo> getPhotos() {
		return photos;
	}

	public static void addPhoto(final PhotoInfo photoInfo) {
		photos.add(photoInfo);
	}

	public static void clearPhotos() {
		photos.clear();
	}
}
