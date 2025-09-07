package io.vertigo.shell.systems.photo;

import java.nio.file.Path;

public record PhotoInfo(Path path, long size, PhotoExifInfo exifInfo, String md5Hash) {
	// All fields, constructor, getters, equals, hashCode, and toString are implicitly defined.
}
