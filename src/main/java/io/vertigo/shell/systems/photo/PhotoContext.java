package io.vertigo.shell.systems.photo;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class PhotoContext {
	private static Path rootPath;
	private static final List<PhotoInfo> photos = new ArrayList<>();

	public static Path getRootPath() {
		return rootPath;
	}

	public static void setRootPath(final Path path) {
		rootPath = path;
	}

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
